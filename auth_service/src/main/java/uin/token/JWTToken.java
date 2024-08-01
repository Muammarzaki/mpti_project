package uin.token;

import com.nimbusds.jose.JOSEObjectType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import uin.AccountEntity;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JWTToken {
    private final Clock clock;
    private final Integer expired;
    private final ChronoUnit timeUnit;
    private final String issuer;
    private final JwtEncoder jwtEncoder;

    public JWTToken(JwtEncoder jwtEncoder, Clock clock, @Value("${jwt.issuer:self}") String issuer, @Value("${jwt.ttl:30d}") String ttl) {
        Matcher matcher = Pattern.compile("^(\\d+)([smH])$").matcher(ttl);
        if (!matcher.matches())
            throw new IllegalStateException("TTL unexpected value: " + ttl);
        this.clock = clock;
        this.issuer = issuer;
        this.jwtEncoder = jwtEncoder;
        this.expired = Integer.valueOf(matcher.group(1));
        String timeunit = matcher.group(2).trim();
        this.timeUnit = switch (timeunit) {
            case "H" -> ChronoUnit.HOURS;
            case "m" -> ChronoUnit.MINUTES;
            case "s" -> ChronoUnit.SECONDS;
            default -> throw new IllegalArgumentException("TTL unexpected value: " + matcher.group(2));
        };
    }


    public String generate(Authentication auth) {
        AccountEntity userDetail = (AccountEntity) auth.getPrincipal();
        Instant now = Instant.now(clock);
        JwsHeader jwsHeader = JwsHeader.with(() -> JwsAlgorithms.RS256)
                .type(JOSEObjectType.JWT.getType())
                .build();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(expired, timeUnit))
                .issuer(issuer)
                .claim("username", userDetail.getUsername())
                .claim("user_id", userDetail.getUserId())
                .claim("roles", userDetail.getAuthorities())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }
}

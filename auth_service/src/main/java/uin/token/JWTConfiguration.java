package uin.token;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

@Configuration
@RestController
public class JWTConfiguration {

    private final String keyId;

    public JWTConfiguration() {
        this.keyId = "rsa_keyID_%s".formatted(Instant.now());
    }

    @Bean("accJwtEncoder")
    JwtEncoder accEncoder(@Qualifier("accRSAKey") JWKSet jwk) {
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(jwk));
    }


    @Bean("accRSAKey")
    JWKSet privateKey(KeyPair keyPair) {
        return new JWKSet(new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(keyId)
                .build());
    }

    @Bean
    KeyPair keyPairGenerate() {
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
            return keyGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to generate RSA key pair", e);
        }
    }

}

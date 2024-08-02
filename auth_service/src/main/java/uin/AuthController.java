package uin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import uin.dto.AccessTokenDTO;
import uin.dto.LoginDTO;
import uin.dto.RegisterDTO;
import uin.dto.SuccessDTO;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object accountRegister(@Valid @RequestBody RegisterDTO registerDTO) {
        accountService.addNewAccount(registerDTO);
        return new SuccessDTO("user register success");
    }

    @PostMapping(value = "login")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenDTO login(@Valid @RequestBody LoginDTO loginDto) {
        return accountService.authenticated(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );
    }
}

package uin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping
    public String hello() {
        return "hello this is a resource server";
    }
}

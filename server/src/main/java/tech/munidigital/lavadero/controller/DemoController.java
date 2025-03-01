package tech.munidigital.lavadero.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping(value = "/demo")
    public String welcome(){
        return "Welcome from a secure endpoint";
    }

}

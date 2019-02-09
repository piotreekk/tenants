package pl.piotrek.tenants.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class GlobalController {

    @RequestMapping("")
    public String info(){
        return "Heellooooo!";
    }

}

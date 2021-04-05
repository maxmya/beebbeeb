package com.trixpert.beebbeeb.api.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class LandingController {

    @RequestMapping("/")
    public String viewLandingPage() {
        return "index";
    }

}

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

    @RequestMapping("/ms91608829.txt")
    public String office() {
        return "{\n" +
                "  \"Description\": \"Domain ownership verification file for Microsoft 365 - place in the website root\",\n" +
                "  \"Domain\": \"beebbeeb.info\",\n" +
                "  \"Id\": \"b33ab60f-fa3f-4a27-afcd-790453a6594a\"\n" +
                "}";
    }

}

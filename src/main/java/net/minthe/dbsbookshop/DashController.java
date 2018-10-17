package net.minthe.dbsbookshop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Controller
public class DashController {
    @RequestMapping("/")
    public String testtest() {
        return "index";
    }
}

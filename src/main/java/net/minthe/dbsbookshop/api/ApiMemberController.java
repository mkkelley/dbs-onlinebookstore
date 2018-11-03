package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.member.LoginService;
import net.minthe.dbsbookshop.member.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Michael Kelley on 11/2/2018
 */
@RestController
@RequestMapping("/api")
public class ApiMemberController {

    private final LoginService loginService;

    @Autowired
    public ApiMemberController(LoginService loginService) {this.loginService = loginService;}

    @PostMapping("/login")
    public boolean login(@RequestBody UserAuthenticationRequest uar) {
        return loginService.authenticate(uar.getUsername(), uar.getPassword());
    }
}

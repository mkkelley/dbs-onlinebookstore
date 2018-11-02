package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Controller
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {this.loginService = loginService;}

    @RequestMapping("/")
    public String redirectRoot() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        UserAuthenticationRequest uar = new UserAuthenticationRequest();
        model.addAttribute("uar", uar);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        loginService.logout();
        redirectAttributes.addFlashAttribute("You have logged out successfully.");
        return "redirect:/login";
    }
}

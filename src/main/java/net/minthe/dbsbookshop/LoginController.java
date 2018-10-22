package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Member;
import net.minthe.dbsbookshop.model.UserAuthenticationRequest;
import net.minthe.dbsbookshop.repo.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public String authenticate(@ModelAttribute UserAuthenticationRequest uar, Model model) {
        String userid = uar.getUserid();
        String password = uar.getPassword();

        boolean memberExists = loginService.useridExists(userid);
        boolean correctPassword = loginService.authenticate(userid, password);
        if (!memberExists) {
            model.addAttribute("message", "Specified member does not exist.");
        } else if (!correctPassword) {
            model.addAttribute("message", "Incorrect password. Please try again.");
        } else {
            model.addAttribute("message", "You have been logged in successfully.");
            return "redirect:/book";
        }
        model.addAttribute("uar", new UserAuthenticationRequest());
        return "login";
    }
}

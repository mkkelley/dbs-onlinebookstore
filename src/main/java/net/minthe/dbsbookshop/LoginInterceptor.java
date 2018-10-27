package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.member.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Michael Kelley on 10/26/2018
 *
 * Disallow access to any page other than login or registration if the user is
 * not logged in.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final LoginService loginService;

    @Autowired
    public LoginInterceptor(LoginService loginService) {this.loginService = loginService;}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!loginService.userLoggedIn() &&
                !request.getServletPath().equals("/login") &&
                !request.getServletPath().equals("/member/new")
        ) {
            response.sendRedirect("/login");
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

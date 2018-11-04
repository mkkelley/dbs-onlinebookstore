package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Michael Kelley on 11/2/2018
 */
@RestController
@RequestMapping("/api")
public class ApiMemberController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    public ApiMemberController(LoginService loginService, MemberRepository memberRepository, MemberService memberService) {
        this.loginService = loginService;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @InitBinder("memberForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new MemberFormValidator(memberRepository));
    }

    @PostMapping("/login")
    public boolean login(@RequestBody UserAuthenticationRequest uar) {
        return loginService.authenticate(uar.getUsername(), uar.getPassword());
    }

    @PostMapping("/member/new")
    public ResponseEntity<?> register(@Valid @RequestBody MemberForm memberForm,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        memberService.saveMember(memberForm);
        return ResponseEntity.ok(null);
    }
}

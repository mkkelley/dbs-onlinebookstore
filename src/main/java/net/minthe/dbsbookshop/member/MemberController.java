package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberRepository memberRepository, MemberService memberService, MemberFormValidator memberFormValidator) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @InitBinder("memberForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new MemberFormValidator(memberRepository));
    }

    @GetMapping(value = "/member")
    public String listMembers(Model model) {
        Iterable<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "member/member_list";
    }

    @GetMapping(value = "/member/new")
    public String newMember(Model model) {
        MemberForm m = new MemberForm();
        model.addAttribute("memberForm", m);
        return "member/member_new";
    }

    @PostMapping(value = "/member/new")
    public String createMember(@Valid @ModelAttribute MemberForm memberForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/member_new";
        }
        memberService.createMember(memberForm);
        return "redirect:/login";
    }

    @RequestMapping("/member/{userid}/edit")
    public String editMember(@PathVariable String userid, Model model) {
        Optional<Member> m = memberRepository.findByUserid(userid);
        if (!m.isPresent()) {
            return "";
        }

        Member member = m.get();
        model.addAttribute("member", member);

        return "member/member_edit";
    }

    @GetMapping("/member/{userid}")
    public String listMembers(@PathVariable String userid, Model model) {
        Optional<Member> m = memberRepository.findByUserid(userid);
        if (!m.isPresent()) {
            return "";
        }

        model.addAttribute("member", m.get());
        return "member/member_show";
    }

    @PostMapping("/member/{userid}")
    public String updateMember(@PathVariable String userid, @ModelAttribute Member member) {
        Optional<Member> m = memberRepository.findByUserid(userid);
        if (!m.isPresent() || !m.get().getUserid().equals(member.getUserid())) {
            return "";
        }
        memberRepository.save(member);

        return "redirect:/member/" + member.getUserid();
    }
}

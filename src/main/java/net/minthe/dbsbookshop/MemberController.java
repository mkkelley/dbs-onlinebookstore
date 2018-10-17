package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Member;
import net.minthe.dbsbookshop.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository) {this.memberRepository = memberRepository;}

    @RequestMapping(value = "/member/new", method = RequestMethod.GET)
    public String newMember(Model model) {
        Member m = new Member();
        model.addAttribute("member", m);
        return "member/member_new";
    }

    @RequestMapping(value="/member/new", method=RequestMethod.POST)
    public String createMember(@ModelAttribute Member member) {
        memberRepository.save(member);
        return "redirect:/member/" + member.getUserid();
    }

    @RequestMapping("/member/edit")
    public String editMember() {
        return "member/member_edit";
    }

    @GetMapping("/member/{userid}")
    public String listMembers(@PathVariable String userid, Model model) {
        Optional<Member> m = memberRepository.getByUserid(userid);
        if (!m.isPresent()) {
            return "";
        }

        model.addAttribute("member", m.get());
        return "member/member_show";
    }
}

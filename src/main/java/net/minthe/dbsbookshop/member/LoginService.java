package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private Member user;

    @Autowired
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.user = null;
    }

    public boolean useridExists(String userid) {
        return memberRepository.findByUserid(userid).isPresent();
    }

    public boolean authenticate(String userid, String password) {
        Optional<Member> o = memberRepository.findByUserid(userid);
        if (!o.isPresent()) {
            return false;
        }

        Member member = o.get();

        if (member.getPassword().equals(password)) {
            user = member;
            return true;
        }
        return false;
    }

    public void logout() {
        user = null;
    }

    public boolean userLoggedIn() {
        return user != null;
    }

    public Member getUser() {
        Optional<Member> memberOptional = memberRepository.findByUserid(this.user.getUserid());

        return memberOptional.orElseThrow();
    }
}

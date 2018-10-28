package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Michael Kelley on 10/28/2018
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {this.memberRepository = memberRepository;}

    public void createMember(MemberForm memberForm) {
        Member m = new Member();
        m.setUserid(memberForm.getUserid());
        m.setPassword(memberForm.getPassword());
        m.setFname(memberForm.getFname());
        m.setLname(memberForm.getLname());
        m.setAddress(memberForm.getAddress());
        m.setCity(memberForm.getCity());
        m.setState(memberForm.getState());
        m.setZip(memberForm.getZip());
        m.setPhone(memberForm.getPhone());
        m.setEmail(memberForm.getEmail());
        if (!memberForm.getCreditcardtype().isEmpty()) {
            m.setCreditcardtype(memberForm.getCreditcardtype());
            m.setCreditcardnumber(memberForm.getCreditcardnumber());
        }
        memberRepository.save(m);
    }
}

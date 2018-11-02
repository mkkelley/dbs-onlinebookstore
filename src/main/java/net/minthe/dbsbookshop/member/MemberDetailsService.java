package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Kelley on 11/2/2018
 */
@Service
public class MemberDetailsService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {this.memberRepository = memberRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> m = memberRepository.findByUserid(username);
        Member member = m.orElseThrow(() -> new UsernameNotFoundException("User " + username + "does not exist."));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        return new User(
                member.getUserid(),
                member.getPassword(),
                grantedAuthorities
        );
    }


}

package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Michael Kelley on 10/15/2018
 */
public interface MemberRepository extends CrudRepository<Member, String> {
    Optional<Member> getByUserid(String userid);
}

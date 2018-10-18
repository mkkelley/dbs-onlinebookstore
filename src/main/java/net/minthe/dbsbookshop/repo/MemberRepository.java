package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
    Optional<Member> findByUserid(String userid);
}

package gdg.hack.connectfriendsserver.service;

import gdg.hack.connectfriendsserver.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

  @Override
  <S extends Member> S save(S entity);

  Optional<Member> findByCardId(Long cardId);

}

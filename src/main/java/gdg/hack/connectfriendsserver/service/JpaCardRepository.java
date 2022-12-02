package gdg.hack.connectfriendsserver.service;

import gdg.hack.connectfriendsserver.domain.Card;
import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardRepository extends JpaRepository<Card, Long> {

  Optional<Card> findById(Long id);

  @Override
  <S extends Card> S save(S entity);

  @Override
  List<Card> findAll();
}

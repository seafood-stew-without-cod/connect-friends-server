package gdg.hack.connectfriendsserver;

import gdg.hack.connectfriendsserver.domain.Card;
import gdg.hack.connectfriendsserver.domain.Member;
import gdg.hack.connectfriendsserver.service.JpaCardRepository;
import gdg.hack.connectfriendsserver.service.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {

  private final JpaCardRepository cardRepository;
  private final JpaMemberRepository memberRepository;

  public boolean isValid() {
    return cardRepository.findAll().isEmpty();
  }

  public void loadData() {
    Card card = new Card("이건창", "010-0000-0000", "email@email.com", "thisisspear",
        "email@email.com", "BACKEND");
    Card savedCard = cardRepository.save(card);
    Member member = new Member(savedCard.getId());
    memberRepository.save(member);
    log.info("card id is : {}", card.getId());
    Card other = new Card("누군가", "010-0000-0100", "other@email.com", "other",
        "email@email.com", "BACKEND");
    cardRepository.save(other);
    memberRepository.save(new Member(other.getId()));

    member.addCards(other.getId());
    memberRepository.save(member);
  }
}

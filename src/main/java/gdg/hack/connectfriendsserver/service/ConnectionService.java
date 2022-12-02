package gdg.hack.connectfriendsserver.service;

import gdg.hack.connectfriendsserver.domain.Card;
import gdg.hack.connectfriendsserver.domain.Member;
import gdg.hack.connectfriendsserver.service.dto.CardRequest;
import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import gdg.hack.connectfriendsserver.service.dto.CardResponses;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConnectionService {
  private final JpaCardRepository cardRepository;
  private final JpaMemberRepository memberRepository;

  public CardResponse findById(Long id) {
    Card card = cardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return getCardResponse(card);
  }

  public CardResponse create(CardRequest cardRequest) {
    Card savedCard = cardRepository.save(getCard(cardRequest));
    Member member = new Member(savedCard.getId());
    memberRepository.save(member);
    return getCardResponse(savedCard);
  }

  public CardResponses findCardsById(Long id, String tag) {
    Member member = memberRepository.findByCardId(id).orElseThrow(IllegalArgumentException::new);

    if (tag == null || tag.isBlank()) {
      return new CardResponses(member.getCardIds().stream()
          .map(i -> cardRepository.findById(i).get())
          .map(this::getCardResponse)
          .collect(Collectors.toList()));
    }

    return new CardResponses(member.getCardIds().stream()
        .map(i -> cardRepository.findById(i).get())
        .filter(card -> card.getTag().equals(tag))
        .map(this::getCardResponse)
        .collect(Collectors.toList()));
  }

  public CardResponse link(Long id, Long cardId) {
    Member member = memberRepository.findByCardId(id).orElseThrow(IllegalArgumentException::new);
    Card card = cardRepository.findById(cardId).orElseThrow(IllegalArgumentException::new);

    if (member.getCardIds().stream().anyMatch(i -> i.equals(cardId))){
      throw new IllegalArgumentException();
    }

    member.addCards(card.getId());
    memberRepository.save(member);
    return getCardResponse(card);
  }

  private CardResponse getCardResponse(Card card) {
    return new CardResponse(
        card.getId(),
        card.getName(),
        card.getPhone(),
        card.getEmail(),
        card.getGit(),
        card.getLinkedInLink(),
        card.getTag()
    );
  }

  private Card getCard(CardRequest cardRequest) {
    return new Card(cardRequest.getName(), cardRequest.getPhone(), cardRequest.getEmail(),
        cardRequest.getGit(), cardRequest.getLinkedIn(), cardRequest.getTag());
  }

}

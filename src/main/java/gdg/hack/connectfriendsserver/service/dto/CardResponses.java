package gdg.hack.connectfriendsserver.service.dto;

import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardResponses {

  private List<CardResponse> cardResponses;

  public CardResponses(List<CardResponse> cardResponses) {
    this.cardResponses = cardResponses;
  }

  public List<CardResponse> getCardResponses() {
    return new ArrayList<>(cardResponses);
  }
}

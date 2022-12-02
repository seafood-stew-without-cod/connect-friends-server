package gdg.hack.connectfriendsserver.service.dto;

import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import java.util.ArrayList;
import java.util.List;

public class CardResponses {

  private List<CardResponse> cardResponses;

  public CardResponses(List<CardResponse> cardResponses) {
    this.cardResponses = cardResponses;
  }

  public List<CardResponse> getCardResponses() {
    return new ArrayList<>(cardResponses);
  }
}

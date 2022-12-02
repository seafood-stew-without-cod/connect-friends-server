package gdg.hack.connectfriendsserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardResponse {

  private final String name;
  private final String phone;
  private final String email;
  private final String git;
  private final String linkedIn;
  private final String tag;
}

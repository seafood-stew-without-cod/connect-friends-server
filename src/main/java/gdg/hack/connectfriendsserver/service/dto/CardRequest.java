package gdg.hack.connectfriendsserver.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardRequest {

  private String name;
  private String phone;
  private String email;
  private String git;
  private String linkedIn;
  private String tag;
}

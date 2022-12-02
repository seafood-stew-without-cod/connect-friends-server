package gdg.hack.connectfriendsserver.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CardResponse {

  private Long cardId;
  private String name;
  private String phone;
  private String email;
  private String git;
  private String linkedIn;
  private String tag;
}

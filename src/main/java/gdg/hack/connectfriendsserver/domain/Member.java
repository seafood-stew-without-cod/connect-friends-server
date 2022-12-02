package gdg.hack.connectfriendsserver.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  private Long cardId;

  @ElementCollection
  private List<Long> cardIds;

  public Member(Long cardId) {
    this.cardId = cardId;
    this.cardIds = new ArrayList<>();
  }

  public void addCards(Long cardId) {
    this.cardIds.add(cardId);
  }
}

package gdg.hack.connectfriendsserver.domain;

import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

  private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private String phone;
  private String email;
  private String git;
  private String linkedInLink;
  private String tag;

  @Builder
  public Card(String name, String phone, String email, String git,
      String linkedInLink, String tag) {

    if (phone == null || phone.isBlank() || !PHONE_PATTERN.matcher(phone).matches()) {
      throw new IllegalArgumentException();
    }

    if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException();
    }

    if (tag == null || tag.isBlank()) {
      throw new IllegalArgumentException();
    }

    this.name = name;
    this.phone = phone;
    this.email = email;
    this.git = git;
    this.linkedInLink = linkedInLink;
    this.tag = tag;
  }
}

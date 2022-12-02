package gdg.hack.connectfriendsserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void invalidEmail() {
    Assertions.assertThatThrownBy(
        () -> Card.builder().name("name").phone("010-3333-4444")
            .email("invalid email").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void invalidPhone() {
    Assertions.assertThatThrownBy(
        () -> Card.builder().name("name").email("email@email.com")
            .phone("invalid phone").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }
}

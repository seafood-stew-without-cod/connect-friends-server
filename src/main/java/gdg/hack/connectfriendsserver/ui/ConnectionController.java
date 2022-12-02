package gdg.hack.connectfriendsserver.ui;

import gdg.hack.connectfriendsserver.service.ConnectionService;
import gdg.hack.connectfriendsserver.service.dto.CardRequest;
import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import gdg.hack.connectfriendsserver.service.dto.CardResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class ConnectionController {

  private final ConnectionService connectionService;

  @PostMapping("me")
  public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest cardRequest) {
    return ResponseEntity.ok(connectionService.create(cardRequest));
  }

  @GetMapping("me")
  public ResponseEntity<CardResponse> card(@RequestParam Long id) {
    return ResponseEntity.ok(connectionService.findById(id));
  }

  @GetMapping
  public ResponseEntity<CardResponses> findCards(@RequestParam Long id,
      @RequestParam(required = false) String tag) {
    return ResponseEntity.ok(connectionService.findCardsById(id, tag));
  }

  @GetMapping("{cardId}")
  public ResponseEntity<CardResponse> link(@RequestParam Long mycard, @PathVariable Long cardId) {
    return ResponseEntity.ok(connectionService.link(mycard, cardId));
  }

}

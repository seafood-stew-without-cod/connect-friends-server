package gdg.hack.connectfriendsserver.ui;

import gdg.hack.connectfriendsserver.CardResponses;
import gdg.hack.connectfriendsserver.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class ConnectionController {

  private final ConnectionService connectionService;

  @PostMapping("me")
  public ResponseEntity<CardResponse> createCard(@RequestHeader String id,
      @RequestBody CardRequest cardRequest) {
    return ResponseEntity.ok(connectionService.create(id, cardRequest));
  }

  @GetMapping("me")
  public ResponseEntity<CardResponse> card(@RequestHeader String id) {
    return ResponseEntity.ok(connectionService.findById(id));
  }

  @GetMapping
  public ResponseEntity<CardResponses> findCards(@RequestHeader String id,
      @RequestParam(required = false) String tag) {
    return ResponseEntity.ok(connectionService.findCardsById(id, tag));
  }

  @PostMapping
  public ResponseEntity<CardResponse> link(@RequestHeader String id, @RequestParam String cardId) {
    return ResponseEntity.ok(connectionService.link(id, cardId));
  }

}

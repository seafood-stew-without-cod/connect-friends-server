package gdg.hack.connectfriendsserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void createAndFindMyCard() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("name", "이건창");
    params.put("phone", "010-0000-0000");
    params.put("email", "email@email.com");
    params.put("git", "this-is-spear");
    params.put("linkedIn", null);
    params.put("tag", "BACKEND");

    CardResponse 생성_결과 = 생성_요청(params);
    Long cardId = 생성_결과.getCardId();

    CardResponse 조회_결과 = 조회_요청(cardId);
    assertThat(생성_결과.getCardId()).isEqualTo(조회_결과.getCardId());
  }

  private CardResponse 조회_요청(Long cardId) throws Exception {
    ResultActions 조회_요청 = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/cards/me")
            .param("id", "" + cardId)
            .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    CardResponse 조회_결과 = objectMapper.readValue(
        조회_요청.andReturn().getResponse().getContentAsString(),
        CardResponse.class);
    return 조회_결과;
  }

  private CardResponse 생성_요청(Map<String, String> params) throws Exception {
    ResultActions 생성_요청 = mockMvc.perform(
        MockMvcRequestBuilders.post("/cards/me")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))
    ).andExpect(status().isOk());

    CardResponse 생성_결과 = objectMapper.readValue(
        생성_요청.andReturn().getResponse().getContentAsString(),
        CardResponse.class);
    return 생성_결과;
  }
}

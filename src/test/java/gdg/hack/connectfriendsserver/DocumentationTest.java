package gdg.hack.connectfriendsserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hack.connectfriendsserver.service.ConnectionService;
import gdg.hack.connectfriendsserver.service.dto.CardResponse;
import gdg.hack.connectfriendsserver.service.dto.CardResponses;
import gdg.hack.connectfriendsserver.ui.ConnectionController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest(ConnectionController.class)
public class DocumentationTest {

  private static final CardResponse 이건창_명함 = new CardResponse(1L, "이건창",
      "010-0000-7000",
      "email@email.com", "this-is-spear", null, "BACKEND");

  private static final CardResponse 다른_명함 = new CardResponse(2L, "누군가", "010-0000-8000",
      "email@email.com", "other", null, "FRONTEND");

  private static final CardResponse 또다른_명함 = new CardResponse(3L, "또다른 누군가",
      "010-0000-0080",
      "email@email.com", "another", null, "FRONTEND");

  private static final CardResponse 기획자_명함 = new CardResponse(4L, "또다른 누군가",
      "010-0000-0090",
      "email@email.com", "another", null, "PLANNING");

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ConnectionService connectionService;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void create() throws Exception {
    when(connectionService.create(any())).thenReturn(이건창_명함);

    Map<String, String> params = new HashMap<>();
    params.put("name", "이건창");
    params.put("phone", "010-0000-000");
    params.put("email", "email@email.com");
    params.put("git", "this-is-spear");
    params.put("linkedIn", null);
    params.put("tag", "BACKEND");

    mockMvc.perform(
        RestDocumentationRequestBuilders
            .post("/cards/me")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))
    ).andDo(
        document(
            "create",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters()
        )
    );
  }

  @Test
  void findCard() throws Exception {
    when(connectionService.findById(any())).thenReturn(이건창_명함);

    mockMvc.perform(
        RestDocumentationRequestBuilders
            .get("/cards/me")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "1")
    ).andDo(
        document(
            "findCard",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters()
        )
    );
  }

  @Test
  void link() throws Exception {
    when(connectionService.link(any(), any())).thenReturn(다른_명함);

    mockMvc.perform(
        RestDocumentationRequestBuilders
            .get("/cards/{cardId}", 1)
            .param("mycard", "23")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(
        document(
            "link",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("cardId").description("카드의 식별자")
            )
        )
    );
  }

  @Test
  void findCards() throws Exception {
    when(connectionService.findCardsById(any(), any())).thenReturn(
        new CardResponses(List.of(다른_명함, 또다른_명함, 기획자_명함))
    );

    mockMvc.perform(
        RestDocumentationRequestBuilders
            .get("/cards")
            .param("id", "123")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(
        document(
            "findCards",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters()
        )
    );
  }

  // find by tag

  @Test
  void findCardsByTag() throws Exception {
    when(connectionService.findCardsById(any(), any())).thenReturn(
        new CardResponses(List.of(다른_명함, 또다른_명함))
    );

    mockMvc.perform(
        RestDocumentationRequestBuilders
            .get("/cards")
            .param("tag", "FRONTEND")
            .param("id", "123")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(
        document(
            "tag",
            requestParameters(
                parameterWithName("tag")
                    .description("tag는 BACKEND, FRONTEND, PLANNING, DESIGN 중 하나의 정보가 들어갑니다."),
                parameterWithName("id")
                    .description("id 값")
            )
        )
    );
  }

  private OperationResponsePreprocessor getDocumentResponse() {
    return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
  }

  private OperationRequestPreprocessor getDocumentRequest() {
    return Preprocessors.preprocessRequest(
        Preprocessors.modifyUris()
            .scheme("http")
            .host("localhost")
            .removePort(),
        Preprocessors.prettyPrint()
    );
  }
}

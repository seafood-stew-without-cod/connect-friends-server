package gdg.hack.connectfriendsserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hack.connectfriendsserver.ui.CardResponse;
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

  private static final CardResponse 이건창_명함 = new CardResponse("이건창", "010-0000-000",
      "email@email.com", "this-is-spear", null, "BACKEND");

  private static final CardResponse 다른_명함 = new CardResponse("누군가", "010-0000-000",
      "email@email.com", "other", null, "FRONTEND");

  private static final CardResponse 또다른_명함 = new CardResponse("또다른 누군가", "010-0000-000",
      "email@email.com", "another", null, "FRONTEND");

  private static final CardResponse 기획자_명함 = new CardResponse("또다른 누군가", "010-0000-000",
      "email@email.com", "another", null, "PLANNING");

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ConnectionService connectionService;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void create() throws Exception {
    when(connectionService.create(any(), any())).thenReturn(이건창_명함);

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
            .header("id", "123-2323-1231-23")
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
            .header("id", "123-2323-1231-23")
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
            .post("/cards")
            .param("cardId", "123-2-31123")
            .header("id", "123-2323-1231-23")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(
        document(
            "link",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters()
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
            .header("id", "123-2323-1231-23")
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
            .header("id", "123-2323-1231-23")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(
        document(
            "tag",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters()
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

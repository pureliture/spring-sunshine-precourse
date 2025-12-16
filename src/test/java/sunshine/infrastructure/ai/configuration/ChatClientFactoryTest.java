package sunshine.infrastructure.ai.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

class ChatClientFactoryTest {

  @Test
  @DisplayName("설정에 따라 올바른 ChatClient를 반환한다")
  void getChatClient() {
    // given
    ChatClient openAiClient = mock(ChatClient.class);
    ChatClient geminiClient = mock(ChatClient.class);
    Map<String, ChatClient> clients = Map.of(
        "openAiChatClient", openAiClient,
        "geminiChatClient", geminiClient
    );

    LlmProperties propsOpenAi = new LlmProperties("openai", true);
    ChatClientFactory factoryOpenAi = new ChatClientFactory(clients, propsOpenAi);

    LlmProperties propsGemini = new LlmProperties("gemini", true);
    ChatClientFactory factoryGemini = new ChatClientFactory(clients, propsGemini);

    // when & then
    assertThat(factoryOpenAi.getChatClient()).isEqualTo(openAiClient);
    assertThat(factoryGemini.getChatClient()).isEqualTo(geminiClient);
  }
}

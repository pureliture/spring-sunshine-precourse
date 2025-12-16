package sunshine.infrastructure.ai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import java.util.Map;
import sunshine.common.exception.model.infrastructure.InfrastructureException;
import sunshine.common.exception.model.ErrorCode;

@Component
public class ChatClientFactory {

  private final Map<String, ChatClient> chatClients;
  private final LlmProperties llmProperties;

  public ChatClientFactory(Map<String, ChatClient> chatClients, LlmProperties llmProperties) {
    this.chatClients = chatClients;
    this.llmProperties = llmProperties;
  }

  public ChatClient getChatClient() {
    String provider = llmProperties.provider();
    if (provider == null || provider.isEmpty()) {
        provider = "openai";
    }

    if ("openai".equalsIgnoreCase(provider) && chatClients.containsKey("openAiChatClient")) {
        return chatClients.get("openAiChatClient");
    }
    if ("gemini".equalsIgnoreCase(provider) && chatClients.containsKey("geminiChatClient")) {
        return chatClients.get("geminiChatClient");
    }

    if (chatClients.containsKey(provider)) {
        return chatClients.get(provider);
    }

    throw new InfrastructureException(ErrorCode.INTERNAL_SERVER_ERROR);
  }
}

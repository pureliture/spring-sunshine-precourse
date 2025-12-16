package sunshine.weather.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import sunshine.infrastructure.ai.configuration.ChatClientFactory;
import sunshine.infrastructure.ai.history.LlmHistory;
import sunshine.infrastructure.ai.history.LlmHistoryRepository;
import sunshine.weather.domain.Weather;

@ExtendWith(MockitoExtension.class)
class OutfitRecommenderTest {

    @Mock
    private ChatClientFactory chatClientFactory;
    @Mock
    private OutfitRecommendationPrompt promptGenerator;
    @Mock
    private LlmHistoryRepository llmHistoryRepository;
    @Mock
    private ChatClient chatClient;
    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;
    @Mock
    private ChatClient.CallResponseSpec callSpec;

    @Test
    @DisplayName("캐시에 없으면 LLM을 호출하고 저장한다")
    void recommend_NoCache() {
        // given
        OutfitRecommendationCache cache = new OutfitRecommendationCache();
        OutfitRecommendationValidator validator = new OutfitRecommendationValidator();
        OutfitRecommender recommender = new OutfitRecommender(chatClientFactory, promptGenerator, llmHistoryRepository, cache, validator);
        Weather weather = new Weather(20.0, 20.0, 50, 5.0, 0, 0.0, 25.0);

        given(chatClientFactory.getChatClient()).willReturn(chatClient);
        given(chatClient.prompt()).willReturn(requestSpec);
        given(requestSpec.user(any(String.class))).willReturn(requestSpec);
        given(requestSpec.call()).willReturn(callSpec);

        // Mock ChatResponse
        ChatResponse chatResponse = mock(ChatResponse.class);
        Generation generation = mock(Generation.class);
        AssistantMessage assistantMessage = new AssistantMessage("Wear a t-shirt");
        given(chatResponse.getResult()).willReturn(generation);
        given(generation.getOutput()).willReturn(assistantMessage);

        ChatResponseMetadata metadata = mock(ChatResponseMetadata.class);
        Usage usage = mock(Usage.class);

        given(usage.getPromptTokens()).willReturn(10);
        given(usage.getCompletionTokens()).willReturn(5);
        given(usage.getTotalTokens()).willReturn(15);

        given(metadata.getUsage()).willReturn(usage);
        given(chatResponse.getMetadata()).willReturn(metadata);

        given(callSpec.chatResponse()).willReturn(chatResponse);

        given(promptGenerator.generatePrompt(any(), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class)))
            .willReturn("Prompt");

        // when
        String result = recommender.recommend("Seoul", weather);

        // then
        assertThat(result).isEqualTo("Wear a t-shirt");
        verify(llmHistoryRepository).save(any(LlmHistory.class));
    }
}

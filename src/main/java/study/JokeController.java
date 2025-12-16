package study;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class JokeController {

    private final ChatClient openAiChatClient;
    private final ChatClient geminiChatClient;

    public JokeController(
            @Qualifier("openai") ChatClient openAiChatClient,
            @Qualifier("gemini") ChatClient geminiChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.geminiChatClient = geminiChatClient;
    }

    @GetMapping("/joke/openai")
    public ChatResponse jokeOpenai(@RequestParam(value = "name", defaultValue = "jo") String name,
                                   @RequestParam(value = "voice", defaultValue = "gentleman") String voice) {
        var userMessage = new UserMessage("""
                    Tell me about three famous pirates from the Golden Age of Piracy and what they did.
                    Write at least one sentence for each pirate.
                """);

        var systemPromptTemplate = new SystemPromptTemplate("""
                You are a helpful AI assistant.
                    You are an AI assistant that helps people find information.
                    Your name is {name}.
                    You should reply to the user's request using your name and in the style of a {voice}.
                """);

        var systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        var prompt = new Prompt(userMessage, systemMessage);

        return openAiChatClient.prompt(prompt).call().chatResponse();
    }

    @GetMapping("/joke/gemini")
    public ChatResponse jokeGemini(@RequestParam(value = "adjective", defaultValue = "Like a gentleman") String adjective,
                             @RequestParam(value = "topic", defaultValue = "software developer") String topic) {
        var promptTemplate = new PromptTemplate("Tell me a {adjective} joke about {topic}");
        var prompt = promptTemplate.render(Map.of("adjective", adjective, "topic", topic));

        return geminiChatClient.prompt(prompt).call().chatResponse();
    }

    @GetMapping("/actors/gemini")
    public ActorsFilms actorsGemini(
            @RequestParam(value = "actor", defaultValue = "brad pit") String actor) {
        var beanOutputConverter = new BeanOutputConverter<>(ActorsFilms.class);
        var format = beanOutputConverter.getFormat();
        var userMessage = """
            Generate the filmography of 5 movies for {actor}.
            {format}
            """;
        var promptTemplate = new PromptTemplate(userMessage).create(Map.of("actor", actor, "format", format));
        return beanOutputConverter.convert(Objects.requireNonNull(geminiChatClient.prompt(promptTemplate).call().chatResponse().getResult().getOutput().getText()));
    }

    @GetMapping("/addDays")
    public String addDays(@RequestParam(defaultValue = "0") int days) {

        var promptTemplate = new PromptTemplate("오늘 기준으로 {days}일 뒤 날짜를 알려줘.");
        var prompt = promptTemplate.render(Map.of("days", days));

        return geminiChatClient.prompt(prompt)
                .tools(new AiFunction())
                .call()
                .content();
    }
}
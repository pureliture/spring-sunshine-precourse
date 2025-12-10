package sunshine.component.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherSummarizerTest {

    private final WeatherSummarizer summarizer = new WeatherSummarizer();

    @Test
    @DisplayName("Generates correct summary string")
    void summarize_GeneratesCorrectString() {
        String summary = summarizer.summarize("Seoul", 23.5, 5.2, 0);
        assertThat(summary).isEqualTo("현재 Seoul의 기온은 23.5°C이며, 풍속은 5.2m/s입니다. 날씨는 맑음입니다.");
    }

    @Test
    @DisplayName("Handles unknown weather code")
    void summarize_UnknownCode() {
        String summary = summarizer.summarize("Tokyo", 10.0, 2.0, 999);
        assertThat(summary).contains("날씨는 알 수 없음입니다.");
    }
}

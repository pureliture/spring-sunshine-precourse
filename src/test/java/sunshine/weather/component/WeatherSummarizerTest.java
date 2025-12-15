package sunshine.weather.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WeatherSummarizerTest {

  @Autowired
  private WeatherSummarizer weatherSummarizer;

  @Test
  @DisplayName("날씨 코드를 포함하여 요약 문장을 생성해야 한다")
  void summarize_GeneratesSummaryWithWeatherDescription() {
    // H2 DB에 0번 코드가 있다고 가정 (flyway로 insert됨)
    String summary = weatherSummarizer.summarize("Seoul", 20.0, 5.0, 0);

    assertThat(summary).contains("Seoul");
    assertThat(summary).contains("20.0°C");
    assertThat(summary).contains("5.0m/s");
    assertThat(summary).contains("맑음");
  }

  @Test
  @DisplayName("알 수 없는 날씨 코드인 경우 기본 메시지를 반환해야 한다")
  void summarize_HandlesUnknownWeatherCode() {
    String summary = weatherSummarizer.summarize("Seoul", 20.0, 5.0, 999);

    assertThat(summary).contains("알 수 없음");
  }
}

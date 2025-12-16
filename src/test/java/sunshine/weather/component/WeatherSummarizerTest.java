package sunshine.weather.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sunshine.weather.domain.WeatherCode;
import sunshine.weather.repository.WeatherCodeRepository;

@ExtendWith(MockitoExtension.class)
class WeatherSummarizerTest {

  @Mock
  private WeatherCodeRepository weatherCodeRepository;

  @InjectMocks
  private WeatherSummarizer weatherSummarizer;

  @Test
  @DisplayName("날씨 코드를 포함하여 요약 문장을 생성해야 한다")
  void summarize_GeneratesSummaryWithWeatherDescription() {
    // given
    int code = 0;
    given(weatherCodeRepository.findById(code)).willReturn(Optional.of(new WeatherCode(code, "맑음")));

    // when
    String summary = weatherSummarizer.summarize("Seoul", 20.0, 5.0, code);

    // then
    assertThat(summary).contains("Seoul");
    assertThat(summary).contains("20.0°C");
    assertThat(summary).contains("5.0m/s");
    assertThat(summary).contains("맑음");
  }

  @Test
  @DisplayName("알 수 없는 날씨 코드인 경우 기본 메시지를 반환해야 한다")
  void summarize_HandlesUnknownWeatherCode() {
    // given
    int code = 999;
    given(weatherCodeRepository.findById(code)).willReturn(Optional.empty());

    // when
    String summary = weatherSummarizer.summarize("Seoul", 20.0, 5.0, code);

    // then
    assertThat(summary).contains("알 수 없음");
  }
}

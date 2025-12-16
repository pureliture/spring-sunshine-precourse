package sunshine.weather.domain;

public interface WeatherSummarizer {

	String getWeatherSummary(String city);

	Boolean getServiceType(String flag);
}

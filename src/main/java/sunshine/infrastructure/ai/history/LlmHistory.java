package sunshine.infrastructure.ai.history;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "llm_history")
public class LlmHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String model;
    private int inputTokens;
    private int outputTokens;
    private int totalTokens;
    private Double estimatedCost;
    private LocalDateTime createdAt;
    private boolean cached;

    protected LlmHistory() {}

    public LlmHistory(String provider, String model, int inputTokens, int outputTokens, int totalTokens, Double estimatedCost, boolean cached) {
        this.provider = provider;
        this.model = model;
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.totalTokens = totalTokens;
        this.estimatedCost = estimatedCost;
        this.createdAt = LocalDateTime.now();
        this.cached = cached;
    }
}

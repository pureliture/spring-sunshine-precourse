package sunshine.infrastructure.ai.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LlmHistoryRepository extends JpaRepository<LlmHistory, Long> {
}

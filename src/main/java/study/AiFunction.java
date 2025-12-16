package study;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

public class AiFunction {

    @Tool
    public DateResponse addDaysFromToday(AddDayRequest request) {

        return new DateResponse(LocalDate.now().plusDays(request.days()).toString());
    }
}


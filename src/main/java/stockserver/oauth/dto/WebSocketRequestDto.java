package stockserver.oauth.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class WebSocketRequestDto {

    @Value("${spring.invest-server.grant-type}")
    private String grant_type;

    @Value("${spring.invest-server.app-key}")
    private String appkey;

    @Value("${spring.invest-server.secret-key}")
    private String secretkey;

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
}

package stockserver.oauth.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public abstract class RequestData implements stockserver.stockinterface.Data {

    // application.yaml의 설정값을 @Value로 주입
    @Value("${spring.invest-server.grant-type}")
    private String grantType;

    @Getter
    @Value("${spring.invest-server.app-key}")
    private String appKey;

    @Getter
    @Value("${spring.invest-server.secret-key}")
    private String secretKey;

    @Getter
    private final Map<String, String> headers;

    @Getter
    private final Object body;

    // 생성자를 통해 초기화
    protected RequestData(Object body) {
        this.body = body;

        // 헤더 초기화
        this.headers = new HashMap<>();
        this.headers.put("grant_type", this.grantType);
        this.headers.put("appKey", this.appKey);
        this.headers.put("secretKey", this.secretKey);
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
}

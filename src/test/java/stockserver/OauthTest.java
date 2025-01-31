package stockserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.socket.server.WebSocketService;
import stockserver.oauth.dto.WebSocketRequestDto;
import stockserver.oauth.service.OauthService;
import stockserver.redis.RedisService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class OauthTest {

    @Autowired
    private OauthService oauthService;
    @Autowired
    private WebSocketRequestDto webSocketRequestDto;
    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("value 작동 성공")
    void getEnvVariableTest(){
        assertNotNull(webSocketRequestDto.getAppkey(), "Appkey should not be null");
    }

    @Test
    @DisplayName("websocketkey 획득 성공")
    void getWebSocketKeyTest() throws JsonProcessingException {
        System.out.println(oauthService.getWebSocketKey());
        assertNotNull(oauthService.getWebSocketKey(), "SockeyKey should not be null");
    }

    @Test
    @DisplayName("Token 획득 성공")
    void getToken() throws JsonProcessingException {
        assertNotNull(oauthService.getToken(), "Token should not be null");
    }

    @Test
    @DisplayName("redis 테스트")
    void setDataRedis() throws JsonProcessingException {
        redisService.insert("test1", "test2");
        assertEquals(redisService.read("test1"), "test2");
    }
}

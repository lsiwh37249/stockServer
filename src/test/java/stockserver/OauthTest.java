package stockserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.socket.server.WebSocketService;
import stockserver.oauth.dto.WebSocketRequestDto;
import stockserver.oauth.service.OauthService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class OauthTest {

    @Autowired
    private OauthService oauthService;
    @Autowired
    private WebSocketRequestDto webSocketRequestDto;

    @Test
    @DisplayName("value 작동 성공")
    void getEnvVariableTest(){
        assertNotNull(webSocketRequestDto.getAppkey(), "Appkey should not be null");
    }

    @Test
    @DisplayName("websocketkey 획득 성공")
    void getWebSocketKeyTest(){
        assertNotNull(oauthService.getWebSocketKey(), "SockeyKey should not be null");
    }

}

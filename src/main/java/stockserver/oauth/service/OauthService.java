package stockserver.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stockserver.oauth.dto.TokenRequestDto;
import stockserver.oauth.dto.WebSocketRequestDto;
import stockserver.redis.RedisService;

import java.time.LocalDateTime;

@Service
public class
OauthService{

    private final WebClient webClient;
    private final WebSocketRequestDto requestDto;
    private final TokenRequestDto tokenRequestDto;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OauthService(WebSocketRequestDto requestDto, TokenRequestDto tokenRequestDto, RedisService redisService, ObjectMapper objectMapper) {
        this.redisService = redisService;
        this.webClient = WebClient.builder()
                .baseUrl("https://openapi.koreainvestment.com:9443")
                .build();
        this.requestDto = requestDto;
        this.tokenRequestDto = tokenRequestDto;
        this.objectMapper = objectMapper;
    }

    // 실시간 데이터
    public String getWebSocketKey() throws JsonProcessingException {
        WebSocketRequestDto webSocketRequestDto = this.requestDto;
        System.out.println(webSocketRequestDto.toJson());
        String response = String.valueOf(webClient.post()
                .uri("/oauth2/Approval") // 요청 URI
                .header("content-type" , "application/json")
                .bodyValue(webSocketRequestDto) // 요청 본문 설정
                .retrieve() // 응답 처리 시작
                .bodyToMono(JsonNode.class) // 응답 본문을 String으로 변환
                .block()); // 비동기 결과를 동기로 변환 (주의: 블록킹)

        System.out.println("POST Response: " + response);
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 파싱
        JsonNode jsonNode = objectMapper.readTree(response);
        // value 추출
        return jsonNode.get("approval_key").asText();
    }
    public String requestToken() throws JsonProcessingException {
        String response = String.valueOf(webClient.post()
                .uri("/oauth2/tokenP") // 요청 URI
                .header("content-type" , "application/json")
                .bodyValue(tokenRequestDto) // 요청 본문 설정
                .retrieve() // 응답 처리 시작
                .bodyToMono(JsonNode.class) // 응답 본문을 String으로 변환
                .block()); // 비동기 결과를 동기로 변환 (주의: 블록킹)

        System.out.println("POST Response: " + response);
        // JSON 파싱
        JsonNode jsonNode = objectMapper.readTree(response);

        //redis에 저장하기
        redisService.insert("tokenInfo", String.valueOf(jsonNode));
        System.out.println(redisService.read("tokenInfo"));

        return jsonNode.get("token_type").asText() + " " + jsonNode.get("access_token").asText();
    }

    public String getToken() throws JsonProcessingException {
        String token = redisService.read("tokenInfo");

        if (token == null) {
            return requestToken();
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(token);
            LocalDateTime tokenExpired = LocalDateTime.parse(jsonNode.get("access_token_token_expired").asText());
            System.out.println(tokenExpired);
            if (tokenExpired.isAfter(LocalDateTime.now().plusDays(1))) {
                return jsonNode.get("token_type").asText() + " " + jsonNode.get("access_token").asText();
            }
            else {
                return requestToken();
            }
        }
        catch(JsonProcessingException e) {
            return requestToken();
        }
    }
}

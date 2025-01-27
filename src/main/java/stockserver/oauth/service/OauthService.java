package stockserver.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stockserver.oauth.dto.TokenRequestDto;
import stockserver.oauth.dto.WebSocketRequestDto;

@Service
public class OauthService{

    private final WebClient webClient;
    private final WebSocketRequestDto requestDto;
    private final TokenRequestDto tokenRequestDto;

    @Autowired
    public OauthService(WebSocketRequestDto requestDto, TokenRequestDto tokenRequestDto) {
        this.webClient = WebClient.builder()
                .baseUrl("https://openapi.koreainvestment.com:9443")
                .build();
        this.requestDto = requestDto;
        this.tokenRequestDto = tokenRequestDto;
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

    public String getToken() throws JsonProcessingException{
        TokenRequestDto tokenRequestDto = this.tokenRequestDto;
        System.out.println(tokenRequestDto.toJson());
        String response = String.valueOf(webClient.post()
                .uri("/oauth2/tokenP") // 요청 URI
                .header("content-type" , "application/json")
                .bodyValue(tokenRequestDto) // 요청 본문 설정
                .retrieve() // 응답 처리 시작
                .bodyToMono(JsonNode.class) // 응답 본문을 String으로 변환
                .block()); // 비동기 결과를 동기로 변환 (주의: 블록킹)

        System.out.println("POST Response: " + response);
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 파싱
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("token_type").asText() + " " + jsonNode.get("access_token").asText();
    }
}

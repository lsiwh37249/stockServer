package stockserver.oauth.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stockserver.oauth.dto.WebSocketRequestDto;

@Service
public class OauthService{

    private final WebClient webClient;
    private final WebSocketRequestDto requestDto;

    @Autowired
    public OauthService(WebSocketRequestDto requestDto) {
        this.webClient = WebClient.builder()
                .baseUrl("https://openapivts.koreainvestment.com:29443")
                .build();
        this.requestDto = requestDto;
    }

    // 실시간 데이터
    public String getWebSocketKey(){
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

        return "approval_key";
    }

    // POST 요청의 RequestBody 값을 암호화 (필수는 아님)
    public String getHashKey(){
        //header + body
        return "hash_key";
    }

    //접속 토큰 발급 acc : 24시간
    public String getAccessToken(){
        return "access_token";
    }

    //접근 토큰 폐기
    public String deleteAccessToken(){
        return "delete_access_token";
    }
}

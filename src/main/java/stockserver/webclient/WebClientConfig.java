package stockserver.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import stockserver.oauth.service.OauthService;

@Configuration
public class WebClientConfig {

    @Getter
    @Value("${spring.invest-server.app-key}")
    private String appkey;

    @Getter
    @Value("${spring.invest-server.secret-key}")
    private String appsecret;

    @Bean
    public WebClient webClient(OauthService oauthService) throws JsonProcessingException {
        return WebClient.builder()
                .baseUrl("https://openapi.koreainvestment.com:9443")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("authorization", oauthService.getToken())
                .defaultHeader("appkey",appkey)
                .defaultHeader("appsecret",appsecret)
                .defaultHeader("tr_id","FHKST03010100")
                .build();
    }
}

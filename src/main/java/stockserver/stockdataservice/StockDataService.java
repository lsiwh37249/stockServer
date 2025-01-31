package stockserver.stockdataservice;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stockserver.oauth.service.OauthService;


@Service
public class StockDataService {

    @Autowired
    private final WebClient webClient;

    public StockDataService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getStockData(String fidInputDate1, String fidInputDate2,
                             String fidPeriodDivCode, String fidOrgAdjPrc){

        String response = String.valueOf(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                        .queryParam("fid_input_date_1", fidInputDate1)
                        .queryParam("fid_input_date_2", fidInputDate2)
                        .queryParam("fid_period_div_code", fidPeriodDivCode)
                        .queryParam("fid_org_adj_prc", fidOrgAdjPrc)
                        .build()) // 요청 URI
                .retrieve() // 응답 처리 시작
                .bodyToMono(JsonNode.class) // 응답 본문을 String으로 변환
                .block()); // 비동기 결과를 동기로 변환 (주의: 블록킹)
        return response;
    }
}

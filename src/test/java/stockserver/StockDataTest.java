package stockserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stockserver.stockdataservice.StockDataService;


@Slf4j
@SpringBootTest
public class StockDataTest {

    private final StockDataService stockDataService;

    @Autowired
    public StockDataTest(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    //특정 주식 가격 정보
    @Test
    @DisplayName("기간별 주식 정보 가지고 오기")
    void getStockData(){
        System.out.println(stockDataService.getStockData("20220101", "20220809","D","1" ));
    }
}

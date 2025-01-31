package stockserver.stockdataservice.requestdata;

import java.util.Map;

public class RequestStockData {

    private Map<String, String> headers;
    private Object body;

    public RequestStockData(Map<String, String> headers, Object body) {
        this.headers = headers;
        this.body = body;
    }
}

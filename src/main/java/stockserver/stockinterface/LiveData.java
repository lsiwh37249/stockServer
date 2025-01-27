package stockserver.stockinterface;

/**
 * 웹소켓 실시간 요청에 필요한 데이터를 나타냅니다.
 */
public interface LiveData extends Data {
    String tradeKey();
}

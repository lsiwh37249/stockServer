package stockserver.stockinterface;

/**
 * API 응답을 나타냅니다.
 */
public interface Response {
    String getErrorDescription();

    String getErrorCode();
}

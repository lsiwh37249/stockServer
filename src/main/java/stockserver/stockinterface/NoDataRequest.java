package stockserver.stockinterface;

/**
 * 데이터가 필요하지 않은 API 요청을 나타냅니다.
 */
public interface NoDataRequest<T extends Response> extends Request<T> {
    public void call();
}

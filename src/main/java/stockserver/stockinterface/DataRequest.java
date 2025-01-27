package stockserver.stockinterface;

/**
 * 데이터가 필요한 API 요청을 나타냅니다.
 */
public interface DataRequest<T extends Data, U extends Response> extends Request<U> {
    public void call();
}

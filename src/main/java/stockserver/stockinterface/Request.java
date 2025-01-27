package stockserver.stockinterface;

import java.util.concurrent.CompletableFuture;

/**
 * API 요청을 나타냅니다.
 */
public interface Request<T extends Response> {
    public void call();
}


package stockserver.stockinterface;

import lombok.Getter;

import java.util.Map;

/**
 * API 요청에 필요한 데이터를 나타냅니다.
 */
public interface Data {

    // 헤더 관련 메서드
    Map<String, String> getHeaders();

    // 바디 관련 메서드
    Object getBody();

}


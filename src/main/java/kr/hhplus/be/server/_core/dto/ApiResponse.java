package kr.hhplus.be.server._core.dto;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    private static <T> ResponseEntity<SingleResult<T>> of(HttpStatus status, T data) {
        return ResponseEntity.status(status).body(new SingleResult<>(null, data));
    }

    private static <T> ResponseEntity<ListResult<T>> of(HttpStatus status, List<T> data) {
        return ResponseEntity.status(status).body(new ListResult<>(null, data));
    }

    public static <T> ResponseEntity<SingleResult<T>> ok(T data) {
        return ApiResponse.of(HttpStatus.OK, data);
    }

    public static <T> ResponseEntity<ListResult<T>> ok(List<T> data) {
        return ApiResponse.of(HttpStatus.OK, data);
    }

    /*
     TODO Exception 생성 시 아래 구칙 적용
        Domain별 Exception 정의 후 각 Exception 추가
     */
    //    public static <T> ResponseEntity<CommonResult> failedOf(UserException e) {
    //        return ResponseEntity.status(400).body(new CommonResult(true, e.getMessage()));
    //    }
}

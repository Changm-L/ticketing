package kr.hhplus.be.server._core.dto;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.hhplus.be.server._core.dto.response.CreateRes;
import kr.hhplus.be.server._core.dto.response.UpdateRes;

public class ApiResponse {

    private static <T> ResponseEntity<SingleResult<T>> of(HttpStatus status, T data) {
        return ResponseEntity.status(status).body(new SingleResult<>(null, data));
    }

    private static <T> ResponseEntity<ListResult<T>> of(HttpStatus status, List<T> data) {
        return ResponseEntity.status(status).body(new ListResult<>(null, data));
    }

    private static ResponseEntity<CommonResult> of(HttpStatus status) {
        return ResponseEntity.status(status).body(new CommonResult(null));
    }

    public static <T> ResponseEntity<SingleResult<T>> ok(T data) {
        return ApiResponse.of(HttpStatus.OK, data);
    }

    public static <T> ResponseEntity<ListResult<T>> ok(List<T> data) {
        return ApiResponse.of(HttpStatus.OK, data);
    }

    public static ResponseEntity<CommonResult> ok() {
        return ApiResponse.of(HttpStatus.OK);
    }

    public static ResponseEntity<CommonResult> failedOf(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new CommonResult(true, message));
    }

    public static ResponseEntity<SingleResult<CreateRes>> create(long id) {
        return ApiResponse.of(HttpStatus.CREATED, new CreateRes(id));
    }

    public static ResponseEntity<SingleResult<UpdateRes>> update(long id) {
        return ApiResponse.of(HttpStatus.OK, new UpdateRes(id));
    }

    /*
     TODO Exception 생성 시 아래 구칙 적용
        Domain별 Exception 정의 후 각 Exception 추가
     */
    //    public static <T> ResponseEntity<CommonResult> failedOf(UserException e) {
    //        return ResponseEntity.status(400).body(new CommonResult(true, e.getMessage()));
    //    }
}

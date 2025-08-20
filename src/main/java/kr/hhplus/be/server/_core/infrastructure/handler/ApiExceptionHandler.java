package kr.hhplus.be.server._core.infrastructure.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    /*
     TODO Exception 생성 시 아래 구칙 적용
        Domain별 Exception 정의 후 각 Exception 추가
     */
    //    @ExceptionHandler(UserException.class)
    //    public ResponseEntity<SingleResult> userFailedOf() {
    //        return ApiResponse.failedOf(e);
    //    }
}

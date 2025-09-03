package kr.hhplus.be.server._core.infrastructure.handler;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.CommonResult;
import kr.hhplus.be.server.domain.auth.exception.UnAuthorizationException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                           .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                           .collect(Collectors.joining(","));

        return ApiResponse.failedOf(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<CommonResult> handleUnAuthorizationException(UnAuthorizationException ex) {
        log.warn(ex.getMessage(), ex);
        return ApiResponse.failedOf(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult> handleException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return ApiResponse.failedOf(HttpStatus.INTERNAL_SERVER_ERROR, "에러가 발생했습니다.");
    }

    /*
     TODO Exception 생성 시 아래 구칙 적용
        Domain별 Exception 정의 후 각 Exception 추가
     */
    //    @ExceptionHandler(UserException.class)
    //    public ResponseEntity<SingleResult> userFailedOf() {
    //        return ApiResponse.failedOf(e);
    //    }
}

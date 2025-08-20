package kr.hhplus.be.server._core.dto;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;

    public SingleResult(String message, T data) {
        super(message);
        this.data = data;
    }
}

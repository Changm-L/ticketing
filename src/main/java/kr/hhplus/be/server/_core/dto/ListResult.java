package kr.hhplus.be.server._core.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ListResult<T> extends CommonResult {
    private final List<T> data;

    public ListResult(String message, List<T> data) {
        super(message);
        this.data = data;
    }
}

package cn.leixd.rpc.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码
 *
 */
@Getter
@AllArgsConstructor
public enum RpcResponseCode {
    SUCCESS(200, "success"),
    FAIL(500, "fail");

    private final int code;
    private final String message;
}

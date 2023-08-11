package com.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CMRespDto<T> {
    private Boolean isSuc;  // true: 성공, false: 실패
    private String msg;
    private T body;
}

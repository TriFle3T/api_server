package com.hug.hug_api.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
public class VerifyResult {
    private boolean success;
    private String email;
}

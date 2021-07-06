package com.name.no.dts.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserStats {

    private Instant ts;
    private Integer userCount;

    public UserStats(Instant ts, Integer userCount) {
        this.ts = ts;
        this.userCount = userCount;
    }
}

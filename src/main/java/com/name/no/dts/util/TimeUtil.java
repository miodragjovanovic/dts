package com.name.no.dts.util;

import java.time.Instant;

public class TimeUtil {

    public static Long getMinuteFromTs(Instant ts) {
        return ts.getEpochSecond() / 60;
    }

    public static Instant getTsFromMinute(Long ts) {
        return Instant.ofEpochSecond(ts * 60);
    }
    
}

package com.name.no.dts.dto;


import lombok.Data;

import java.time.Instant;

@Data
public class Message {

    private String uid;
    private Instant ts;

}

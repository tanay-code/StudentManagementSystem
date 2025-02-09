package com.cts.sms.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AttendanceStatus {
    PRESENT, ABSENT, NA;

    @JsonCreator
    public static AttendanceStatus fromString(String value) {
        return AttendanceStatus.valueOf(value.toUpperCase());
    }
}
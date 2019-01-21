package com.mahindra.util;

public enum ExcelTitle {
    NO("NO", "NO"),
    Redundant("Redundant", "Redundant"),
    RepBlock("RepBlock", "RepBlock"),
    TAGNAME("测点名", "TAG NAME"),
    DESCRIPTION("中文描述", "DESCRIPTION"),
    SIGTYPE("信号类型", "SIG TYPE"),
    SENSORTYPE("测点类型", "SENSOR TYPE"),
    PID("P&ID", "P&ID");

    // 成员变量
    private String name;
    private String code;

    ExcelTitle(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

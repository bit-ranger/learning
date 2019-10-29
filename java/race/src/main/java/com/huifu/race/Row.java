package com.huifu.race;

import java.util.List;

public class Row {

    private String line;
    private List<String> columns;

    public Row(String line, List<String> columns) {
        this.line = line;
        this.columns = columns;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<String> getColumns() {
        return columns;
    }
}

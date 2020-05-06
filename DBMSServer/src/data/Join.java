package data;

import java.io.Serializable;

public class Join implements Serializable {
    private Table table;
    private String attribute1;
    private String attribute2;

    public Join(Table table, String attribute1, String attribute2) {
        this.table = table;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }
}

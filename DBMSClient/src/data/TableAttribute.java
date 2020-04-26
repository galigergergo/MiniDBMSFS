package data;

import java.io.Serializable;

public class TableAttribute implements Serializable {
    private String tableName;
    private String attributeName;

    public TableAttribute(String tableName, String attributeName) {
        this.tableName = tableName;
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return tableName + " - " + attributeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}

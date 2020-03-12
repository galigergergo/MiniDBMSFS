package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Database implements Serializable {
    private String dataBaseName;
    private ArrayList<Table> tables;

    public Database(String dataBaseName) {
        this.dataBaseName = dataBaseName;
        tables = new ArrayList<>();
    }

    @Override
    public String toString() {
        return dataBaseName;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }
}

package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private String tableName;
    private String fileName;
    private int rowLength;
    private ArrayList<Attribute> attributes;
    private String pKAttrName;
    private ArrayList<ForeignKey> foreignKeys;
    private ArrayList<IndexFile> indexFiles;

    public Table(String tableName, String fileName, int rowLength, String pKAttrName) {
        this.tableName = tableName;
        this.fileName = fileName;
        this.rowLength = rowLength;
        this.pKAttrName = pKAttrName;
        attributes = new ArrayList<>();
        indexFiles = new ArrayList<>();
        foreignKeys = new ArrayList<>();
    }

    @Override
    public String toString() {
        return tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }

    public String getpKAttrName() {
        return pKAttrName;
    }

    public void setpKAttrName(String pKAttrName) {
        this.pKAttrName = pKAttrName;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public ArrayList<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        foreignKeys.add(foreignKey);
    }

    public ArrayList<IndexFile> getIndexFiles() {
        return indexFiles;
    }

    public void addIndexFile(IndexFile indexFile) {
        indexFiles.add(indexFile);
    }
}

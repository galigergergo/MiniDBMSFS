package data;

public class Table {
    String tableName;
    String fileName;
    int rowLength;
    Attribute attributes[];
    String pKAttrName;
    ForeignKey foreignKeys[];
    IndexFile indexFiles[];
}

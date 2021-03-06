package data;

import java.io.Serializable;
import java.util.ArrayList;

// 4:08 PM join start
// 10:03:34 PM WWHTHTHTHFJFHFFH. still no STAAAAP

public class Selection implements Serializable {
    private String database;
    private Table table;
    private ArrayList<Join> joins;
    private ArrayList<String> functions;
    private ArrayList<TableAttribute> attributes;
    private ArrayList<WhereCondition> conditions;
    private TableAttribute groupByAttribute;
    private WhereCondition havingCondition;
    private String havingFunction;

    public String getHavingFunction() {
        return havingFunction;
    }

    public void setHavingFunction(String havingFunction) {
        this.havingFunction = havingFunction;
    }

    public Selection() {
        joins = new ArrayList<>();
        functions = new ArrayList<>();
        attributes = new ArrayList<>();
        conditions = new ArrayList<>();
    }

    public void empty() {
        joins.clear();
        functions.clear();
        attributes.clear();
        conditions.clear();
    }

    public WhereCondition getHavingCondition() {
        return havingCondition;
    }

    public void setHavingCondition(WhereCondition havingCondition) {
        this.havingCondition = havingCondition;
    }

    public void addJoin(Table table, TableAttribute attr1, String attr2) {
        joins.add(new Join(table, attr1, attr2));
    }

    public void addFunction(String func) {
        functions.add(func);
    }

    public void addAttribute(TableAttribute attr) {
        attributes.add(attr);
    }

    public void addCondition(WhereCondition c) {
        conditions.add(c);
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public ArrayList<Join> getJoins() {
        return joins;
    }

    public void setJoins(ArrayList<Join> joins) {
        this.joins = joins;
    }

    public ArrayList<String> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<String> functions) {
        this.functions = functions;
    }

    public ArrayList<TableAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<TableAttribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<WhereCondition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    public TableAttribute getGroupByAttribute() {
        return groupByAttribute;
    }

    public void setGroupByAttribute(TableAttribute groupByAttribute) {
        this.groupByAttribute = groupByAttribute;
    }
}

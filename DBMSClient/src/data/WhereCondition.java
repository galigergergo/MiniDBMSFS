package data;

public class WhereCondition {
    private TableAttribute attribute;
    private String operator;
    private String value;

    public WhereCondition(TableAttribute attribute, String operator, String value) {
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    public TableAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(TableAttribute attribute) {
        this.attribute = attribute;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

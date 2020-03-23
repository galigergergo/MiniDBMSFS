package data;

import java.io.Serializable;

public class Condition implements Serializable {
    private String attribute;
    private String operator;
    private String value;

    public Condition(String attribute, String operator, String value) {
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}

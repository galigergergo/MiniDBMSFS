package data;

import java.io.Serializable;

public class Attribute implements Serializable {
    String attributeName;
    String type;
    int length;
    boolean isnull;

    public Attribute(String attributeName, String type, int length, boolean isnull) {
        this.attributeName = attributeName;
        this.type = type;
        this.length = length;
        this.isnull = isnull;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIsnull() {
        return isnull;
    }

    public void setIsnull(boolean isnull) {
        this.isnull = isnull;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
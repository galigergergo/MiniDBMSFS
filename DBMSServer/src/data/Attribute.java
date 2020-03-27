package data;

import java.io.Serializable;

public class Attribute implements Serializable {
    String attributeName;
    String type;
    int length;
    boolean isUnique;

    public Attribute(String attributeName, String type, int length, boolean isUnique) {
        this.attributeName = attributeName;
        this.type = type;
        this.length = length;
        this.isUnique = isUnique;
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

    public boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}

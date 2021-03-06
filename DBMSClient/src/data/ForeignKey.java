package data;

import java.io.Serializable;

public class ForeignKey implements Serializable {
    private String attrName;
    private String refTableName;
    private String refAttrName;

    public ForeignKey(String attrName, String refTableName, String refAttrName) {
        this.attrName = attrName;
        this.refTableName = refTableName;
        this.refAttrName = refAttrName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getRefAttrName() {
        return refAttrName;
    }

    public void setRefAttrName(String refAttrName) {
        this.refAttrName = refAttrName;
    }
}

package data;

import java.io.Serializable;
import java.util.ArrayList;

public class IndexFile implements Serializable {
    private String indexName;
    private boolean isUnique;
    private String attribute;

    public IndexFile(String indexName, boolean isUnique, String attribute) {
        this.indexName = indexName;
        this.isUnique = isUnique;
        this.attribute = attribute;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}

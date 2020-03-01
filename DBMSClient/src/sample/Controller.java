package sample;

import data.*;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView<String> treeView;
    private ArrayList<Database> databases;

    public void initialize() {
        databases = new ArrayList<>();
        Database db = new Database("db1");
        Table tb = new Table("t1", "t1.ta", 300, "asd");
        Attribute attr = new Attribute("att1", "varchar", 30, false);
        tb.addAttribute(attr);
        db.addTable(tb);
        databases.add(db);

        initTreeView();
    }

    private void initTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Databases");

        for(Database temp : databases) {
            System.out.println("Asd");
            TreeItem<String> tableItem = new TreeItem<>("");
            for(Table table : temp.getTables()) {
                tableItem.setValue(table.getTableName());
                TreeItem<String> attrsItem = new TreeItem<>("Attributes");
                TreeItem<String> indexItem = new TreeItem<>("Index Files");
                for(Attribute attr : table.getAttributes()) {
                    TreeItem<String> attrItem = new TreeItem<>("");
                    if(table.getpKAttrName().equals(attr.getAttributeName())) {
                        attrItem.setValue("PK " + attr.getAttributeName());
                    }
                    else {
                        for(ForeignKey fk : table.getForeignKeys()) {
                            if(fk.getAttrName().equals(attr.getAttributeName())) {
                                attrItem.setValue("FK " + attr.getAttributeName());
                            }
                            else {
                                attrItem.setValue(attr.getAttributeName());
                            }
                        }
                    }
                    attrsItem.getChildren().add(attrItem);
                }
                for(IndexFile index : table.getIndexFiles()) {
                    TreeItem<String> indItem = new TreeItem<>(index.getIndexName());
                    for(String attr : index.getAttributes()) {
                        TreeItem<String> attrItem = new TreeItem<>(attr);
                        indItem.getChildren().add(attrItem);
                    }
                    indexItem.getChildren().add(indItem);
                }
                attrsItem.getChildren().add(attrsItem);
                attrsItem.getChildren().add(indexItem);
            }
            rootItem.getChildren().add(tableItem);
        }

        treeView.setRoot(rootItem);
    }
}

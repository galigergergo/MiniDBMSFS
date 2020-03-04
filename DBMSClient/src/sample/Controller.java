package sample;

import data.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView<String> treeView;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem newDatabase;
    @FXML
    private MenuItem newTable;
    @FXML
    private MenuItem newIndexFile;
    @FXML
    private MenuItem delDatabase;
    @FXML
    private MenuItem delTable;
    @FXML
    private MenuItem delIndexFile;
    @FXML
    private Pane ndbPane;
    @FXML
    private Button ndbCancelButton;
    @FXML
    private Button ndbOkButton;
    @FXML
    private TextField ndbTextField;


    private ArrayList<Database> databases;

    public void initialize() {
        // get database list from the server
        databases = new ArrayList<>();
        Database db = new Database("db1");
        Table tb = new Table("t1", "t1.ta", 300, "asd");
        Attribute attr = new Attribute("att1", "varchar", 30, false);
        Attribute attr2 = new Attribute("att2", "varchar", 30, false);
        tb.addAttribute(attr2);
        ForeignKey fk = new ForeignKey("att1", "tb2", "att1");
        tb.addAttribute(attr);
        tb.addForeignKey(fk);
        db.addTable(tb);
        Database db2 = new Database("db2");
        databases.add(db);
        databases.add(db2);

        // initialize tree view
        initTreeView();

        // create new database
        newDatabase.setOnAction(e -> ndbPane.setVisible(true));
        ndbCancelButton.setOnAction(e -> ndbPane.setVisible(false));
        ndbOkButton.setOnAction(e -> {
            // send to the server
            System.out.println("ndb " + ndbTextField.getText());
            ndbPane.setVisible(false);
        });

        // create new table
        newTable.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });

        // create new index file
        newIndexFile.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });

        // delete database
        delDatabase.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });

        // delete table
        delTable.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });

        // delete index file
        delIndexFile.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });
    }

    private void initTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Databases");

        for(Database temp : databases) {
            TreeItem<String> dbItem = new TreeItem<>(temp.getDataBaseName());
            for(Table table : temp.getTables()) {
                TreeItem<String> tableItem = new TreeItem<>(table.getTableName());
                TreeItem<String> attrsItem = new TreeItem<>("Attributes");
                TreeItem<String> indexItem = new TreeItem<>("Index Files");
                for(Attribute attr : table.getAttributes()) {
                    TreeItem<String> attrItem = new TreeItem<>("");
                    if(table.getpKAttrName().equals(attr.getAttributeName())) {
                        attrItem.setValue("PK " + attr.getAttributeName());
                    }
                    else {
                        if(table.getForeignKeys().size() > 0) {
                            for(ForeignKey fk : table.getForeignKeys()) {
                                if(fk.getAttrName().equals(attr.getAttributeName())) {
                                    attrItem.setValue("FK " + attr.getAttributeName());
                                }
                                else {
                                    attrItem.setValue(attr.getAttributeName());
                                }
                            }
                        }
                        else {
                            attrItem.setValue(attr.getAttributeName());
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
                tableItem.getChildren().add(indexItem);
                tableItem.getChildren().add(attrsItem);
                dbItem.getChildren().add(tableItem);
            }
            rootItem.getChildren().add(dbItem);
        }

        treeView.setRoot(rootItem);
    }
}

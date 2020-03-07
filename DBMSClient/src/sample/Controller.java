package sample;

import data.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView<String> treeView;
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
    @FXML
    private Pane ddbPane;
    @FXML
    private Button ddbCancelButton;
    @FXML
    private Button ddbOkButton;
    @FXML
    private ChoiceBox<Database> ddbChoiceBox;
    @FXML
    private Pane dtPane;
    @FXML
    private Button dtCancelButton;
    @FXML
    private Button dtOkButton;
    @FXML
    private ChoiceBox<Table> dtChoiceBoxT;
    @FXML
    private ChoiceBox<Database> dtChoiceBoxDb;
    @FXML
    private Pane ntPane;
    @FXML
    private Button ntCancelButton;
    @FXML
    private Button ntOkButton;
    @FXML
    private ChoiceBox<String> ntChoiceBoxT;
    @FXML
    private TextField ntTextFieldT;
    @FXML
    private ChoiceBox<Database> ntChoiceBoxDb;
    @FXML
    private TextField ntTextFieldN;
    @FXML
    private TextField ntTextFieldS;
    @FXML
    private TableView ntTableView;
    @FXML
    private Button ntLeftButton;
    @FXML
    private Button ntRightButton;
    @FXML
    private ChoiceBox<String> ntChoiceBoxPK;
    @FXML
    private TableColumn nameColumn;
    private ObservableList<String> selectedRow = FXCollections.observableArrayList();

    private ArrayList<Database> databases;
    private String[] attrTypes = {"int", "char", "varchar", "date"};

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
        newDatabase.setOnAction(e -> {
            hidePanes();
            ndbPane.setVisible(true);
        });
        ndbCancelButton.setOnAction(e -> ndbPane.setVisible(false));
        ndbOkButton.setOnAction(e -> {
            // send to the server
            if (!ndbTextField.getText().equals("")) {
                System.out.println("ndb " + ndbTextField.getText());
                ndbPane.setVisible(false);
            }
        });

        // create new table
        newTable.setOnAction(e -> {
            // create tableview columns
            final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Name");
            column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
            ntTableView.getColumns().add(column1);
            final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Type");
            column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
            ntTableView.getColumns().add(column2);
            final TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Size");
            column3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
            ntTableView.getColumns().add(column3);

            ntChoiceBoxDb.setValue(null);
            ntChoiceBoxDb.getItems().clear();
            // fill choice box
            for(Database temp : databases) {
                ntChoiceBoxDb.getItems().add(temp);
            }
            ntChoiceBoxT.setValue(null);
            ntChoiceBoxT.getItems().clear();
            // fill choice box
            for(String temp : attrTypes) {
                ntChoiceBoxT.getItems().add(temp);
            }
            ntPane.setVisible(true);
        });
        ntCancelButton.setOnAction(e -> {
            hidePanes();
            ntPane.setVisible(false);
        });
        // content of table view
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        ntRightButton.setOnAction(e -> {
            // insert into table view
            if(!(ntTextFieldN.getText().equals("") || ntChoiceBoxT.getValue() == null || (ntTextFieldS.getText().equals("") && ntChoiceBoxT.getValue() == "varchar") || (!ntTextFieldS.getText().equals("") && ntChoiceBoxT.getValue() != "varchar"))) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(ntTextFieldN.getText());
                row.add(ntChoiceBoxT.getValue());
                row.add(ntTextFieldS.getText());
                data.add(row);
                ntTableView.setItems(data);
                ntTextFieldN.setText("");
                ntChoiceBoxT.setValue(null);
                ntTextFieldS.setText("");
                ntChoiceBoxPK.getItems().clear();
                for(ObservableList<String> s : data) {
                    ntChoiceBoxPK.getItems().add(s.get(0));
                }
            }
        });
        // update selected row of table view
        ntTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(ntTableView.getSelectionModel().getSelectedItem() != null)
                    setSelectedRow((ObservableList<String>) ntTableView.getSelectionModel().getSelectedItem());
            }
        });
        ntLeftButton.setOnAction(e -> {
            data.remove(selectedRow);
            ntTextFieldN.setText(selectedRow.get(0));
            ntChoiceBoxT.setValue(selectedRow.get(1));
            ntTextFieldS.setText(selectedRow.get(2));
            ntChoiceBoxPK.getItems().clear();
            for(ObservableList<String> s : data) {
                ntChoiceBoxPK.getItems().add(s.get(0));
            }
        });
        ntOkButton.setOnAction(e -> {
            // send to the server
            if (!(ntChoiceBoxDb.getValue() == null || ntTextFieldT.getText().equals("") || ntChoiceBoxPK.getValue() == null)) {
                System.out.println("nt " + ntTextFieldT.getText());
                ntPane.setVisible(false);
            }
        });

        // create new index file
        newIndexFile.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });

        // delete database
        delDatabase.setOnAction(e -> {
            ddbChoiceBox.setValue(null);
            ddbChoiceBox.getItems().clear();
            // fill choice box
            for(Database temp : databases) {
                ddbChoiceBox.getItems().add(temp);
            }
            hidePanes();
            ddbPane.setVisible(true);
        });
        ddbCancelButton.setOnAction(e -> ddbPane.setVisible(false));
        ddbOkButton.setOnAction(e -> {
            // send to the server
            if(ddbChoiceBox.getValue() != null) {
                System.out.println("ddb " + ddbChoiceBox.getValue().getDataBaseName());
                ddbPane.setVisible(false);
            }
        });

        // delete table
        delTable.setOnAction(e -> {
            dtChoiceBoxDb.setValue(null);
            dtChoiceBoxDb.getItems().clear();
            // fill database choice box
            for(Database temp : databases) {
                dtChoiceBoxDb.getItems().add(temp);
            }
            hidePanes();
            dtPane.setVisible(true);
        });
        dtChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            dtChoiceBoxT.setValue(null);
            dtChoiceBoxT.getItems().clear();
            for (Table temp : current.getTables()) {
                dtChoiceBoxT.getItems().add(temp);
            }
        });
        dtCancelButton.setOnAction(e -> dtPane.setVisible(false));
        dtOkButton.setOnAction(e -> {
            // send to the server
            if(dtChoiceBoxDb.getValue() != null && dtChoiceBoxT.getValue() != null) {
                System.out.println("dt " + dtChoiceBoxDb.getValue().getDataBaseName() + " " + dtChoiceBoxT.getValue().getTableName());
                dtPane.setVisible(false);
            }
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

    // hide all the option panes before creating a new one
    private void hidePanes() {
        ndbPane.setVisible(false);
        ddbPane.setVisible(false);
        ntPane.setVisible(false);
        dtPane.setVisible(false);
    }

    public void setSelectedRow(ObservableList<String> list) {
        selectedRow = list;
    }

}




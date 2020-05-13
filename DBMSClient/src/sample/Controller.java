package sample;

import data.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
    private MenuItem insert;
    @FXML
    private MenuItem delete;
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
    private Button refreshButton;
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
    private Pane ntfkPane1;
    @FXML
    private Pane ntfkPane2;
    @FXML
    private Button ntCancelButton;
    @FXML
    private Button ntfkOKButton;
    @FXML
    private Button ntNextButton;
    @FXML
    private ChoiceBox<String> ntChoiceBoxT;
    @FXML
    private TextField ntTextFieldT;
    @FXML
    private ChoiceBox<Database> ntChoiceBoxDb;
    @FXML
    private ChoiceBox<String> ntChoiceBoxU;
    @FXML
    private TextField ntTextFieldN;
    @FXML
    private TextField ntTextFieldS;
    @FXML
    private TableView<ObservableList<String>> ntTableView;
    @FXML
    private Button ntLeftButton;
    @FXML
    private Button ntRightButton;
    @FXML
    private ChoiceBox<String> ntChoiceBoxPK;
    @FXML
    private ChoiceBox<Table> ntfkChoiceBoxT;
    @FXML
    private ChoiceBox<String> ntfkChoiceBoxA;
    @FXML
    private Pane niPane;
    @FXML
    private ChoiceBox<Database> niChoiceBoxDb;
    @FXML
    private ChoiceBox<Table> niChoiceBoxT;
    @FXML
    private ChoiceBox<String> niChoiceBoxA;
    @FXML
    private TextField niTextFieldN;
    @FXML
    private Button niCancelButton;
    @FXML
    private Button niOKButton;
    @FXML
    private Pane insertPane;
    @FXML
    private Pane deletePane;
    @FXML
    private Button insertCancelButton;
    @FXML
    private Button insertOKButton;
    @FXML
    private Button insertLeftButton;
    @FXML
    private Button insertRightButton;
    @FXML
    private Button deleteCancelButton;
    @FXML
    private Button deleteOKButton;
    @FXML
    private ChoiceBox<Database> deleteChoiceBoxDb;
    @FXML
    private ChoiceBox<Table> deleteChoiceBoxT;
    @FXML
    private ChoiceBox<Attribute> deleteChoiceBoxA;
    @FXML
    private ChoiceBox<String> deleteChoiceBoxO;
    @FXML
    private TextField deleteTextFieldV;
    @FXML
    private TextField insertTextFieldV;
    @FXML
    private ChoiceBox<Attribute> insertChoiceBoxA;
    @FXML
    private ChoiceBox<Database> insertChoiceBoxDb;
    @FXML
    private ChoiceBox<Table> insertChoiceBoxT;
    @FXML
    private TableView<ObservableList<String>> insertTableView;
    @FXML
    private Pane selFromPane;
    @FXML
    private ChoiceBox<Database> selFromChoiceBoxDb;
    @FXML
    private ChoiceBox<Table> selFromChoiceBoxT;
    @FXML
    private Button selFromAttrButton;
    @FXML
    private Button selFromJoinButton;
    @FXML
    private Button selFromCancelButton;
    @FXML
    private Pane selJoinPane;
    @FXML
    private ChoiceBox<Table> selJoinChoiceBoxT;
    @FXML
    private ChoiceBox<TableAttribute> selJoinChoiceBoxA1;
    @FXML
    private ChoiceBox<Attribute> selJoinChoiceBoxA2;
    @FXML
    private Button selJoinAttrButton;
    @FXML
    private Button selJoinJoinButton;
    @FXML
    private Button selJoinCancelButton;
    @FXML
    private Pane selAttrPane;
    @FXML
    private ChoiceBox<String> selAttrChoiceBoxF;
    @FXML
    private ChoiceBox<TableAttribute> selAttrChoiceBoxA;
    @FXML
    private TableView<ObservableList<String>> selAttrTableView;
    @FXML
    private Button selAttrLeftButton;
    @FXML
    private Button selAttrRightButton;
    @FXML
    private Button selAttrOkButton;
    @FXML
    private Button selAttrWhereButton;
    @FXML
    private Button selAttrCancelButton;
    @FXML
    private Pane selWherePane;
    @FXML
    private ChoiceBox<TableAttribute> selWhereChoiceBoxA;
    @FXML
    private ChoiceBox<String> selWhereChoiceBoxO;
    @FXML
    private TextField selWhereTextFieldV;
    @FXML
    private TableView<ObservableList<String>> selWhereTableView;
    @FXML
    private Button selWhereLeftButton;
    @FXML
    private Button selWhereRightButton;
    @FXML
    private Button selWhereOkButton;
    @FXML
    private Button selWhereGroupByButton;
    @FXML
    private Button selWhereCancelButton;
    @FXML
    private Pane selGroupByPane;
    @FXML
    private ChoiceBox<TableAttribute> selGroupByChoiceBoxA;
    @FXML
    private Button selGroupByOkButton;
    @FXML
    private Button selGroupByCancelButton;
    @FXML
    private Pane selOutputPane;
    @FXML
    private TableView<ObservableList<String>> selOutputTableView;
    @FXML
    private Button selOutputCancelButton;
    @FXML
    private MenuItem select;
    @FXML
    private CheckBox selAttrCheckBox;

    // selected rows of table views
    private ObservableList<String> selectedRow = FXCollections.observableArrayList();

    private List<Database> databases;
    private final String[] attrTypes = {"int", "char", "varchar", "date"};
    private final String[] operatorTypes = {"=", "!=", "<", ">", "<=", ">="};
    private final String[] uniqueList = {"unique", "not unique"};
    private final String[] functionList = {"count", "max"};
    public void initialize() throws Exception{
        // establish connection with server
        Socket socket = new Socket("localhost", 54321);
        System.out.println("connection established");

        // data sending and receiving variables
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

        // start server input listener
        Thread socketListenerThread = new Thread(new MySocketListener(this, is));
        socketListenerThread.start();

        // get database list from the server
        try {
            os.flush();
            os.writeUTF("start");
            os.flush();
        } catch (Exception ex) {
            System.out.println("Error! Transmit message: start");
        }

        // initialize tree view
        refreshButton.setOnAction(e -> initTreeView());

        // create new database
        newDatabase.setOnAction(e -> {
            ndbTextField.setText("");
            hidePanes();
            ndbPane.setVisible(true);
        });
        ndbCancelButton.setOnAction(e -> ndbPane.setVisible(false));
        ndbOkButton.setOnAction(e -> {
            // send to the server
            if (!ndbTextField.getText().equals("")) {
                try {
                    Database newDb = new Database(ndbTextField.getText());
                    os.writeUTF("ndb");
                    os.flush();
                    os.writeObject(newDb);
                    os.flush();
                    System.out.println(newDb);
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: ndb");
                }
                ndbPane.setVisible(false);
            }
        });

        // create new table
        // content of table view
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        newTable.setOnAction(e -> {
            ntChoiceBoxDb.setValue(null);
            ntChoiceBoxDb.setDisable(false);
            ntTextFieldT.setText("");
            ntTextFieldT.setDisable(false);
            ntTextFieldN.setText("");
            ntChoiceBoxT.setValue(null);
            ntTextFieldS.setText("");
            ntChoiceBoxPK.setValue(null);
            ntTableView.getItems().clear();
            data.clear();
            ntfkChoiceBoxT.setValue(null);
            ntfkChoiceBoxA.setValue(null);
            hidePanes();
            ntTableView.getColumns().clear();

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
            final TableColumn<ObservableList<String>, String> column4 = new TableColumn<>("Unique");
            column4.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
            ntTableView.getColumns().add(column4);
            final TableColumn<ObservableList<String>, String> column5 = new TableColumn<>("Ref. T");
            column5.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)));
            ntTableView.getColumns().add(column5);
            final TableColumn<ObservableList<String>, String> column6 = new TableColumn<>("Ref. A");
            column6.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(5)));
            ntTableView.getColumns().add(column6);

            ntChoiceBoxDb.setValue(null);
            ntChoiceBoxDb.getItems().clear();
            // fill choice box
            for (Database temp : databases) {
                ntChoiceBoxDb.getItems().add(temp);
            }
            ntChoiceBoxT.setValue(null);
            ntChoiceBoxT.getItems().clear();
            // fill choice box
            for (String temp : attrTypes) {
                ntChoiceBoxT.getItems().add(temp);
            }
            ntChoiceBoxU.getItems().clear();
            // fill choice box
            for (String temp : uniqueList) {
                ntChoiceBoxU.getItems().add(temp);
            }
            ntChoiceBoxU.setValue("not unique");
            ntPane.setVisible(true);
        });
        ntCancelButton.setOnAction(e -> hidePanes());
        ntRightButton.setOnAction(e -> {
            // insert into table view
            if (!(ntTextFieldN.getText().equals("") || ntChoiceBoxT.getValue() == null || ((!Pattern.matches("-?\\d+", ntTextFieldS.getText()) || ntTextFieldS.getText().equals("")) && ntChoiceBoxT.getValue().equals("varchar")) || (!ntTextFieldS.getText().equals("") && !ntChoiceBoxT.getValue().equals("varchar")))) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(ntTextFieldN.getText());
                row.add(ntChoiceBoxT.getValue());
                row.add(ntTextFieldS.getText());
                row.add(ntChoiceBoxU.getValue());
                row.add("");
                row.add("");
                data.add(row);
                ntTableView.setItems(data);
                ntTextFieldN.setText("");
                ntChoiceBoxT.setValue(null);
                ntTextFieldS.setText("");
                ntChoiceBoxPK.getItems().clear();
                for (ObservableList<String> s : data) {
                    ntChoiceBoxPK.getItems().add(s.get(0));
                }
            }
        });
        // update selected row of table view
        ntTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super ObservableList<String>>) (observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (ntTableView.getSelectionModel().getSelectedItem() != null) {
                setSelectedRow(ntTableView.getSelectionModel().getSelectedItem());
            }
        });
        ntLeftButton.setOnAction(e -> {
            data.remove(selectedRow);
            ntTextFieldN.setText(selectedRow.get(0));
            ntChoiceBoxT.setValue(selectedRow.get(1));
            ntTextFieldS.setText(selectedRow.get(2));
            ntChoiceBoxPK.getItems().clear();
            for (ObservableList<String> s : data) {
                ntChoiceBoxPK.getItems().add(s.get(0));
            }
        });
        ntNextButton.setOnAction(e -> {
            if (!(ntChoiceBoxDb.getValue() == null || ntTextFieldT.getText().equals("") || ntChoiceBoxPK.getValue() == null)) {
                // fill Fk table choice box
                ntfkChoiceBoxT.setValue(null);
                ntfkChoiceBoxT.getItems().clear();
                for (Table temp : ntChoiceBoxDb.getValue().getTables()) {
                    ntfkChoiceBoxT.getItems().add(temp);
                }
                // fill Fk attribute choice box
                ntfkChoiceBoxT.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
                    if (ntfkChoiceBoxT.getValue() != null) {
                        if (ntfkChoiceBoxT.getValue() != null) {
                            ntfkChoiceBoxA.setValue(null);
                            ntfkChoiceBoxA.getItems().clear();
                            for (Attribute temp : ntfkChoiceBoxT.getValue().getAttributes()) {
                                ntfkChoiceBoxA.getItems().add(temp.getAttributeName());
                            }
                        }
                    }
                });
                // when attribute chosen, update data list
                ntfkChoiceBoxA.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
                    if (ntfkChoiceBoxA.getValue() != null) {
                        if (selectedRow.size() > 0 && !selectedRow.get(0).equals(ntChoiceBoxPK.getValue()) && ntfkChoiceBoxA.getValue() != null) {
                            ObservableList<String> selRow = selectedRow;
                            data.remove(selectedRow);
                            selRow.remove(5);
                            selRow.remove(4);
                            selRow.add(ntfkChoiceBoxT.getValue().getTableName());
                            selRow.add(ntfkChoiceBoxA.getValue());
                            data.add(selRow);
                            ntTableView.setItems(data);
                            ntTableView.refresh();
                        }
                    }
                });
                ntfkPane1.setVisible(true);
                ntfkPane2.setVisible(true);
                ntChoiceBoxDb.setDisable(true);
                ntTextFieldT.setDisable(true);
            }
        });
        ntfkOKButton.setOnAction(e -> {
            // send to the server
            try {
                Table newT = new Table(ntTextFieldT.getText(), "afilename", 123, ntChoiceBoxPK.getValue());
                for(ObservableList<String> row : data) {
                    // attributes
                    int size = 0;
                    if (!row.get(2).equals(""))
                        size = Integer.parseInt(row.get(2));
                    Attribute att = new Attribute(row.get(0), row.get(1), size, false);
                    if (row.get(3).equals("unique")) {
                        att.setIsUnique(true);
                    }
                    if (att.getAttributeName().equals(ntChoiceBoxPK.getValue())) {
                        att.setIsUnique(true);
                    }
                    newT.addAttribute(att);

                    // foreign keys
                    if(!row.get(4).equals("")) {
                        ForeignKey forK = new ForeignKey(row.get(0), row.get(4), row.get(5));
                        newT.addForeignKey(forK);
                    }
                }
                os.writeUTF("nt");
                os.flush();
                os.writeUTF(ntChoiceBoxDb.getValue().getDataBaseName());
                os.flush();
                os.writeObject(newT);
                os.flush();
            } catch (Exception ex) {
                System.out.println("Error! Transmit message: nt");
            }
            ntPane.setVisible(false);
        });

        // create new index file
        // content of table view
        ObservableList<ObservableList<String>> content = FXCollections.observableArrayList();
        newIndexFile.setOnAction(e -> {
            hidePanes();
            niChoiceBoxA.setValue(null);
            niTextFieldN.setText("");
            content.clear();

            final TableColumn<ObservableList<String>, String> column = new TableColumn<>("Attribute");
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));

            niChoiceBoxDb.setValue(null);
            niChoiceBoxDb.getItems().clear();
            // fill choice box
            for (Database temp : databases) {
                niChoiceBoxDb.getItems().add(temp);
            }
            niPane.setVisible(true);
        });
        niChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (niChoiceBoxDb.getValue() != null) {
                niChoiceBoxT.setValue(null);
                niChoiceBoxT.getItems().clear();
                for (Table temp : current.getTables()) {
                    niChoiceBoxT.getItems().add(temp);
                }
            }
        });
        niChoiceBoxT.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (niChoiceBoxT.getValue() != null) {
                niChoiceBoxA.setValue(null);
                niChoiceBoxA.getItems().clear();
                for (Attribute temp : current.getAttributes()) {
                    niChoiceBoxA.getItems().add(temp.getAttributeName());
                }
            }
        });
        niCancelButton.setOnAction(e -> hidePanes());
        niOKButton.setOnAction(e -> {
            // send to the server
            if(niChoiceBoxT.getValue() != null && !niTextFieldN.getText().equals("")) {
                // send to the server
                // ITT KENE A UNIQUE-ot betenni                           \/
                IndexFile newI = new IndexFile(niTextFieldN.getText(), false, niChoiceBoxA.getValue());
                try {
                    os.writeUTF("ni");
                    os.flush();
                    os.writeUTF(niChoiceBoxDb.getValue().getDataBaseName());
                    os.flush();
                    os.writeUTF(niChoiceBoxT.getValue().getTableName());
                    os.flush();
                    os.writeObject(newI);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: ni");
                }
                niPane.setVisible(false);
            }
        });

        // delete database
        delDatabase.setOnAction(e -> {
            ddbChoiceBox.setValue(null);
            ddbChoiceBox.getItems().clear();
            // fill choice box
            for (Database temp : databases) {
                ddbChoiceBox.getItems().add(temp);
            }
            hidePanes();
            ddbPane.setVisible(true);
        });
        ddbCancelButton.setOnAction(e -> ddbPane.setVisible(false));
        ddbOkButton.setOnAction(e -> {
            if (ddbChoiceBox.getValue() != null) {
                // send to the server
                try {
                    os.writeUTF("ddb");
                    os.flush();
                    os.writeUTF(ddbChoiceBox.getValue().getDataBaseName());
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: ddb");
                }
                ddbPane.setVisible(false);
            }
        });

        // delete table
        delTable.setOnAction(e -> {
            dtChoiceBoxDb.setValue(null);
            dtChoiceBoxDb.getItems().clear();
            // fill database choice box
            for (Database temp : databases) {
                dtChoiceBoxDb.getItems().add(temp);
            }
            hidePanes();
            dtPane.setVisible(true);
        });
        dtChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (dtChoiceBoxDb.getValue() != null) {
                dtChoiceBoxT.setValue(null);
                dtChoiceBoxT.getItems().clear();
                for (Table temp : current.getTables()) {
                    dtChoiceBoxT.getItems().add(temp);
                }
            }
        });
        dtCancelButton.setOnAction(e -> dtPane.setVisible(false));
        dtOkButton.setOnAction(e -> {
            if (dtChoiceBoxDb.getValue() != null && dtChoiceBoxT.getValue() != null) {
                // send to the server
                try {
                    os.writeUTF("dt");
                    os.flush();
                    os.writeUTF(dtChoiceBoxDb.getValue().getDataBaseName());
                    os.flush();
                    os.writeUTF(dtChoiceBoxT.getValue().getTableName());
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: dt");
                }
                dtPane.setVisible(false);
            }
        });

        // insert
        // content of table view
        ObservableList<ObservableList<String>> insertData = FXCollections.observableArrayList();
        insert.setOnAction(e -> {
            insertTextFieldV.setText("");
            insertChoiceBoxA.setValue(null);
            insertChoiceBoxT.setValue(null);
            insertChoiceBoxDb.setValue(null);
            insertTableView.getItems().clear();

            // create tableview columns
            final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Attribute");
            column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
            insertTableView.getColumns().add(column1);
            final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Value");
            column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
            insertTableView.getColumns().add(column2);

            insertChoiceBoxDb.setValue(null);
            insertChoiceBoxDb.getItems().clear();
            // fill database choice box
            for (Database temp : databases) {
                insertChoiceBoxDb.getItems().add(temp);
            }

            hidePanes();
            insertPane.setVisible(true);
        });
        insertChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (insertChoiceBoxDb.getValue() != null) {
                insertChoiceBoxT.setValue(null);
                insertChoiceBoxT.getItems().clear();
                for (Table temp : current.getTables()) {
                    insertChoiceBoxT.getItems().add(temp);
                }
            }
        });
        insertChoiceBoxT.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (insertChoiceBoxT.getValue() != null) {
                insertChoiceBoxA.setValue(null);
                insertChoiceBoxA.getItems().clear();
                for (Attribute temp : current.getAttributes()) {
                    insertChoiceBoxA.getItems().add(temp);
                }
            }
        });
        insertRightButton.setOnAction(e -> {
            // insert into table view
            if((insertChoiceBoxA.getValue() != null) && (insertChoiceBoxA.getValue().getType().equals("varchar")
                    || (insertChoiceBoxA.getValue().getType().equals("int") && Pattern.matches("-?\\d+", insertTextFieldV.getText()))
                    || (insertChoiceBoxA.getValue().getType().equals("char") && insertTextFieldV.getText().length() == 1)
                    || (insertChoiceBoxA.getValue().getType().equals("date")))) {
                ObservableList<String> row = FXCollections.observableArrayList();
                String attr = insertChoiceBoxA.getValue().getAttributeName();
                row.add(attr);
                row.add(insertTextFieldV.getText());
                insertData.add(row);
                insertTableView.setItems(insertData);
                insertTextFieldV.setText("");
                insertChoiceBoxA.setValue(null);
                for(Attribute attrib : insertChoiceBoxT.getValue().getAttributes()) {
                    if(attrib.getAttributeName().equals(attr)) {
                        insertChoiceBoxA.getItems().remove(attrib);
                    }
                }
            }
        });
        // update selected row of table view
        insertTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super ObservableList<String>>) (observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (insertTableView.getSelectionModel().getSelectedItem() != null) {
                setSelectedRow(insertTableView.getSelectionModel().getSelectedItem());
            }
        });
        insertLeftButton.setOnAction(e -> {
            String at = selectedRow.get(0);
            insertData.remove(selectedRow);
            for(Attribute attr : insertChoiceBoxT.getValue().getAttributes()) {
                if(attr.getAttributeName().equals(at)) {
                    insertChoiceBoxA.getItems().add(attr);
                }
            }
        });
        insertCancelButton.setOnAction(e -> insertPane.setVisible(false));
        insertOKButton.setOnAction(e -> {
            // verify is PK is included in table view
            boolean pkUsed = false;
            for (ObservableList<String> row : insertData) {
                if (row.get(0).equals(insertChoiceBoxT.getValue().getpKAttrName())) {
                    pkUsed = true;
                    break;
                }
            }
            if (pkUsed && insertChoiceBoxDb.getValue() != null && insertChoiceBoxT.getValue() != null) {
                // send to the server
                try {
                    String key = "";
                    // attributes to the values
                    StringBuilder attrs = new StringBuilder();
                    StringBuilder value = new StringBuilder();
                    for (ObservableList<String> row : insertData) {
                        if (row.get(0).equals(insertChoiceBoxT.getValue().getpKAttrName()))
                            key = row.get(1);
                    }
                    for (Attribute attr : insertChoiceBoxT.getValue().getAttributes()) {
                        for (ObservableList<String> row : insertData) {
                            if (!row.get(0).equals(insertChoiceBoxT.getValue().getpKAttrName()) && row.get(0).equals(attr.getAttributeName())) {
                                value.append(row.get(1));
                            }
                            else if (row.get(0).equals(attr.getAttributeName()) && row.get(1).equals("")) {
                                value.append("NULL");
                            }
                        }
                        if (!attr.getAttributeName().equals(insertChoiceBoxT.getValue().getpKAttrName())) {
                            attrs.append(attr.getAttributeName()).append("#");
                            value.append("#");
                        }
                    }

                    os.writeUTF("insert");
                    os.flush();
                    os.writeUTF(insertChoiceBoxDb.getValue().getDataBaseName());
                    os.flush();
                    os.writeUTF(insertChoiceBoxT.getValue().getTableName());
                    os.flush();
                    os.writeUTF(key);
                    os.flush();
                    os.writeUTF(attrs.toString());
                    os.flush();
                    os.writeUTF(value.toString());
                    os.flush();

                    insertPane.setVisible(false);
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: insert");
                }
            }
        });

        // delete
        delete.setOnAction(e -> {
            deleteTextFieldV.setText("");
            deleteChoiceBoxDb.setValue(null);
            deleteChoiceBoxT.setValue(null);
            deleteChoiceBoxA.setValue(null);
            deleteChoiceBoxO.setValue(null);

            deleteChoiceBoxDb.getItems().clear();
            // fill database choice box
            for (Database temp : databases) {
                deleteChoiceBoxDb.getItems().add(temp);
            }

            deleteChoiceBoxO.getItems().clear();
            // fill choice box
            for (String temp : operatorTypes) {
                deleteChoiceBoxO.getItems().add(temp);
            }

            hidePanes();
            deletePane.setVisible(true);
        });
        deleteChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (deleteChoiceBoxDb.getValue() != null) {
                deleteChoiceBoxT.setValue(null);
                deleteChoiceBoxT.getItems().clear();
                for (Table temp : current.getTables()) {
                    deleteChoiceBoxT.getItems().add(temp);
                }
            }
        });
        deleteChoiceBoxT.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (deleteChoiceBoxT.getValue() != null) {
                deleteChoiceBoxA.setValue(null);
                deleteChoiceBoxA.getItems().clear();
                for (Attribute temp : current.getAttributes()) {
                    deleteChoiceBoxA.getItems().add(temp);
                }
            }
        });
        deleteCancelButton.setOnAction(e -> deletePane.setVisible(false));
        deleteOKButton.setOnAction(e -> {
            if (deleteChoiceBoxDb.getValue() != null && deleteChoiceBoxT.getValue() != null && deleteChoiceBoxA.getValue() != null && deleteChoiceBoxO.getValue() != null && !deleteTextFieldV.getText().equals("")
                    && ((deleteChoiceBoxA.getValue().getType().equals("varchar"))
                        || (deleteChoiceBoxA.getValue().getType().equals("int") && Pattern.matches("-?\\d+", deleteTextFieldV.getText()))
                        || (deleteChoiceBoxA.getValue().getType().equals("char") && deleteTextFieldV.getText().length() == 1)
                        || (deleteChoiceBoxA.getValue().getType().equals("date")))) {
                // send to the server
                try {
                    os.writeUTF("delete");
                    os.flush();
                    os.writeUTF(deleteChoiceBoxDb.getValue().getDataBaseName());
                    os.flush();
                    os.writeUTF(deleteChoiceBoxT.getValue().getTableName());
                    os.flush();
                    Condition condition = new Condition(deleteChoiceBoxA.getValue().getAttributeName(), deleteChoiceBoxO.getValue(), deleteTextFieldV.getText());
                    os.writeObject(condition);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: delete");
                }
                deletePane.setVisible(false);
            }
        });


        // select - from
        ArrayList<WhereCondition> conds = new ArrayList<>();
        ArrayList<String> functions = new ArrayList<>();
        ArrayList<TableAttribute> attributes = new ArrayList<>();
        Selection selection = new Selection();
        select.setOnAction(e -> {
            selFromChoiceBoxDb.setValue(null);
            selFromChoiceBoxT.setValue(null);

            selFromChoiceBoxDb.getItems().clear();
            // fill database choice box
            for (Database temp : databases) {
                selFromChoiceBoxDb.getItems().add(temp);
            }

            // new selection
            selection.empty();

            hidePanes();
            selFromPane.setVisible(true);
        });
        selFromChoiceBoxDb.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (selFromChoiceBoxDb.getValue() != null) {
                selFromChoiceBoxT.setValue(null);
                selFromChoiceBoxT.getItems().clear();
                for (Table temp : current.getTables()) {
                    selFromChoiceBoxT.getItems().add(temp);
                }
            }
        });
        selFromCancelButton.setOnAction(e -> selFromPane.setVisible(false));
        selFromJoinButton.setOnAction(e -> {
            if (selFromChoiceBoxDb.getValue() != null && selFromChoiceBoxT.getValue() != null) {
                selection.setDatabase(selFromChoiceBoxDb.getValue().getDataBaseName());
                selection.setTable(selFromChoiceBoxT.getValue());

                selJoinChoiceBoxT.setValue(null);
                selJoinChoiceBoxT.getItems().clear();
                selJoinChoiceBoxA1.setValue(null);
                selJoinChoiceBoxA1.getItems().clear();
                selJoinChoiceBoxA2.setValue(null);
                selJoinChoiceBoxA2.getItems().clear();

                for (Table temp : selFromChoiceBoxDb.getValue().getTables()) {
                    selJoinChoiceBoxT.getItems().add(temp);
                }
                for (Attribute temp : selFromChoiceBoxT.getValue().getAttributes()) {
                    selJoinChoiceBoxA1.getItems().add(new TableAttribute(selFromChoiceBoxT.getValue().getTableName(), temp.getAttributeName()));
                }

                selFromPane.setVisible(false);
                selJoinPane.setVisible(true);
            }
        });
        selFromAttrButton.setOnAction(e -> {
            if (selFromChoiceBoxDb.getValue() != null && selFromChoiceBoxT.getValue() != null) {
                selection.setDatabase(selFromChoiceBoxDb.getValue().getDataBaseName());
                selection.setTable(selFromChoiceBoxT.getValue());

                selAttrChoiceBoxF.setValue(null);
                selAttrChoiceBoxF.getItems().clear();
                selAttrChoiceBoxA.setValue(null);
                selAttrChoiceBoxA.getItems().clear();
                selAttrCheckBox.setSelected(false);

                for (String temp : functionList) {
                    selAttrChoiceBoxF.getItems().add(temp);
                }
                for (Attribute temp : selection.getTable().getAttributes()) {
                    selAttrChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute temp : j.getTable().getAttributes()) {
                        selAttrChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), temp.getAttributeName()));
                    }
                }

                data.clear();
                selAttrTableView.getColumns().clear();

                // create tableview columns
                final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Attribute");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
                selAttrTableView.getColumns().add(column1);
                final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Function");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
                selAttrTableView.getColumns().add(column2);

                functions.clear();
                attributes.clear();

                selFromPane.setVisible(false);
                selAttrPane.setVisible(true);
            }
        });

        // select - join
        selJoinChoiceBoxT.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> {
            if (selJoinChoiceBoxT.getValue() != null) {
                selJoinChoiceBoxA2.setValue(null);
                selJoinChoiceBoxA2.getItems().clear();
                for (Attribute temp : current.getAttributes()) {
                    selJoinChoiceBoxA2.getItems().add(temp);
                }
            }
        });
        selJoinCancelButton.setOnAction(e -> selJoinPane.setVisible(false));
        selJoinJoinButton.setOnAction(e -> {
            if (selJoinChoiceBoxT.getValue() != null && selJoinChoiceBoxA1.getValue() != null && selJoinChoiceBoxA2.getValue() != null) {
                selection.addJoin(selJoinChoiceBoxT.getValue(), selJoinChoiceBoxA1.getValue(), selJoinChoiceBoxA2.getValue().getAttributeName());

                selJoinChoiceBoxT.setValue(null);
                selJoinChoiceBoxT.getItems().clear();
                selJoinChoiceBoxA1.setValue(null);
                selJoinChoiceBoxA1.getItems().clear();
                selJoinChoiceBoxA2.setValue(null);
                selJoinChoiceBoxA2.getItems().clear();

                for (Table temp : selFromChoiceBoxDb.getValue().getTables()) {
                    selJoinChoiceBoxT.getItems().add(temp);
                }
                for (Attribute temp : selFromChoiceBoxT.getValue().getAttributes()) {
                    selJoinChoiceBoxA1.getItems().add(new TableAttribute(selFromChoiceBoxT.getValue().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute a : j.getTable().getAttributes()) {
                        selJoinChoiceBoxA1.getItems().add(new TableAttribute(j.getTable().getTableName(), a.getAttributeName()));
                    }
                }
            }
        });
        selJoinAttrButton.setOnAction(e -> {
            if (selJoinChoiceBoxT.getValue() != null && selJoinChoiceBoxA1.getValue() != null && selJoinChoiceBoxA2.getValue() != null) {
                selection.addJoin(selJoinChoiceBoxT.getValue(), selJoinChoiceBoxA1.getValue(), selJoinChoiceBoxA2.getValue().getAttributeName());

                selAttrChoiceBoxF.setValue(null);
                selAttrChoiceBoxF.getItems().clear();
                selAttrChoiceBoxA.setValue(null);
                selAttrChoiceBoxA.getItems().clear();
                selAttrCheckBox.setSelected(false);

                for (String temp : functionList) {
                    selAttrChoiceBoxF.getItems().add(temp);
                }
                for (Attribute temp : selection.getTable().getAttributes()) {
                    selAttrChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute temp : j.getTable().getAttributes()) {
                        selAttrChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), temp.getAttributeName()));
                    }
                }

                data.clear();
                selAttrTableView.getColumns().clear();

                // create tableview columns
                final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Attribute");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
                selAttrTableView.getColumns().add(column1);
                final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Function");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
                selAttrTableView.getColumns().add(column2);

                functions.clear();
                attributes.clear();

                selFromPane.setVisible(false);
                selAttrPane.setVisible(true);
            }
        });

        // select - attributes
        selAttrRightButton.setOnAction(e -> {
            // insert into table view
            if(selAttrChoiceBoxA.getValue() != null) {
                ObservableList<String> row = FXCollections.observableArrayList();
                TableAttribute attr = selAttrChoiceBoxA.getValue();
                row.add(attr.toString());
                attributes.add(attr);
                if (selAttrChoiceBoxF.getValue() == null) {
                    row.add("");
                    functions.add("");
                }
                else {
                    row.add(selAttrChoiceBoxF.getValue());
                    functions.add(selAttrChoiceBoxF.getValue());
                }
                data.add(row);
                selAttrTableView.setItems(data);
                selAttrChoiceBoxF.setValue(null);
                selAttrChoiceBoxA.setValue(null);

                int i = 0;
                for(TableAttribute attrib : selAttrChoiceBoxA.getItems()) {
                    if(attrib.toString().equals(attr.toString())) {
                        break;
                    }
                    i++;
                }
                selAttrChoiceBoxA.getItems().remove(i);
            }
        });
        // update selected row of table view
        selAttrTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super ObservableList<String>>) (observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (selAttrTableView.getSelectionModel().getSelectedItem() != null) {
                setSelectedRow(selAttrTableView.getSelectionModel().getSelectedItem());
            }
        });
        selAttrLeftButton.setOnAction(e -> {
            if (selectedRow != null) {
                String at = selectedRow.get(0);
                data.remove(selectedRow);

                int i = 0;
                for(TableAttribute attr : attributes) {
                    if (at.equals(attr.toString())) {
                        break;
                    }
                    i++;
                }
                attributes.remove(i);
                functions.remove(i);

                for(Attribute attrib : selection.getTable().getAttributes()) {
                    String name = selection.getTable().getTableName() + " - " + attrib.getAttributeName();
                    if(name.equals(at)) {
                        selAttrChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), attrib.getAttributeName()));
                    }
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute attrib : j.getTable().getAttributes()) {
                        String name = j.getTable().getTableName() + " - " + attrib.getAttributeName();
                        if(name.equals(at)) {
                            selAttrChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), attrib.getAttributeName()));
                        }
                    }
                }
            }
        });
        selAttrCancelButton.setOnAction(e -> selAttrPane.setVisible(false));
        selAttrOkButton.setOnAction(e -> {
            if (selAttrCheckBox.isSelected()) {
                for(Attribute attr : selection.getTable().getAttributes()) {
                    selection.addAttribute(new TableAttribute(selection.getTable().getTableName(), attr.getAttributeName()));
                    selection.addFunction("");
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute attr : j.getTable().getAttributes()) {
                        selection.addAttribute(new TableAttribute(j.getTable().getTableName(), attr.getAttributeName()));
                        selection.addFunction("");
                    }
                }

                // send to the server
                try {
                    os.writeUTF("select");
                    os.flush();
                    os.writeObject(selection);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: select");
                }

                selAttrPane.setVisible(false);
            }
            else if (attributes.size() > 0) {
                for(String func : functions) {
                    selection.addFunction(func);
                }
                for(TableAttribute attr : attributes) {
                    selection.addAttribute(attr);
                }

                // send to the server
                try {
                    os.writeUTF("select");
                    os.flush();
                    os.writeObject(selection);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: select");
                }

                selAttrPane.setVisible(false);
            }
        });
        selAttrWhereButton.setOnAction(e -> {
            if (selAttrCheckBox.isSelected()) {
                for (Attribute attr : selection.getTable().getAttributes()) {
                    selection.addAttribute(new TableAttribute(selection.getTable().getTableName(), attr.getAttributeName()));
                    selection.addFunction("");
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute attr : j.getTable().getAttributes()) {
                        selection.addAttribute(new TableAttribute(j.getTable().getTableName(), attr.getAttributeName()));
                        selection.addFunction("");
                    }
                }

                selWhereChoiceBoxA.setValue(null);
                selWhereChoiceBoxA.getItems().clear();
                selWhereChoiceBoxO.setValue(null);
                selWhereChoiceBoxO.getItems().clear();
                selWhereTextFieldV.setText("");

                for (Attribute temp : selection.getTable().getAttributes()) {
                    selWhereChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute temp : j.getTable().getAttributes()) {
                        selWhereChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), temp.getAttributeName()));
                    }
                }
                for (String temp : operatorTypes) {
                    selWhereChoiceBoxO.getItems().add(temp);
                }

                data.clear();
                selWhereTableView.getColumns().clear();

                // create tableview columns
                final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Attribute");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
                selWhereTableView.getColumns().add(column1);
                final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Operator");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
                selWhereTableView.getColumns().add(column2);
                final TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Value");
                column3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
                selWhereTableView.getColumns().add(column3);

                selAttrPane.setVisible(false);
                selWherePane.setVisible(true);
            }
            else if (attributes.size() > 0) {
                for(String func : functions) {
                    selection.addFunction(func);
                }
                for(TableAttribute attr : attributes) {
                    selection.addAttribute(attr);
                }

                selWhereChoiceBoxA.setValue(null);
                selWhereChoiceBoxA.getItems().clear();
                selWhereChoiceBoxO.setValue(null);
                selWhereChoiceBoxO.getItems().clear();
                selWhereTextFieldV.setText("");

                for (Attribute temp : selection.getTable().getAttributes()) {
                    selWhereChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute temp : j.getTable().getAttributes()) {
                        selWhereChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), temp.getAttributeName()));
                    }
                }
                for (String temp : operatorTypes) {
                    selWhereChoiceBoxO.getItems().add(temp);
                }

                data.clear();
                selWhereTableView.getColumns().clear();

                // create tableview columns
                final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Attribute");
                column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
                selWhereTableView.getColumns().add(column1);
                final TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Operator");
                column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
                selWhereTableView.getColumns().add(column2);
                final TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Value");
                column3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
                selWhereTableView.getColumns().add(column3);

                selAttrPane.setVisible(false);
                selWherePane.setVisible(true);
            }
        });

        // select - where
        selWhereRightButton.setOnAction(e -> {
            // insert into table view
            if(selWhereChoiceBoxA.getValue() != null && selWhereChoiceBoxO.getValue() != null && !selWhereTextFieldV.getText().equals("")) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(selWhereChoiceBoxA.getValue().toString());
                row.add(selWhereChoiceBoxO.getValue());
                row.add(selWhereTextFieldV.getText());
                conds.add(new WhereCondition(selWhereChoiceBoxA.getValue(), selWhereChoiceBoxO.getValue(), selWhereTextFieldV.getText()));
                data.add(row);
                selWhereTableView.setItems(data);

                selWhereChoiceBoxA.setValue(null);
                selWhereChoiceBoxO.setValue(null);
                selWhereTextFieldV.setText("");
            }
        });
        // update selected row of table view
        selWhereTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super ObservableList<String>>) (observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (selWhereTableView.getSelectionModel().getSelectedItem() != null) {
                setSelectedRow(selWhereTableView.getSelectionModel().getSelectedItem());
            }
        });
        selWhereLeftButton.setOnAction(e -> {
            if (selectedRow != null) {
                String at = selectedRow.get(0);
                data.remove(selectedRow);

                int i = 0;
                for(WhereCondition c : conds) {
                    if (at.equals(c.getAttribute().toString())) {
                        break;
                    }
                    i++;
                }
                conds.remove(i);
            }
        });
        selWhereCancelButton.setOnAction(e -> selWherePane.setVisible(false));
        selWhereOkButton.setOnAction(e -> {
            if (conds.size() > 0) {
                for(WhereCondition c : conds) {
                    selection.addCondition(c);
                }
// db print
//                System.out.println("db=" + selection.getDatabase());
//                System.out.println("table=" + selection.getTable().getTableName());
//                System.out.println("attribsPROJ:");
//                for (TableAttribute ta : selection.getAttributes()) {
//                    System.out.println("tableAttrName=" + ta.getTableName());
//                    System.out.println("tableAttr=" + ta.getAttributeName());
//                }
//                System.out.println("\nCONDattrsSEL:");
//                for (WhereCondition wc : selection.getConditions()) {
//                    System.out.print("condition=" + wc.getAttribute().getAttributeName());
//                    System.out.print(" " + wc.getOperator());
//                    System.out.println(" " + wc.getValue());
//                }

                // send to the server
                try {
                    os.writeUTF("select");
                    os.flush();
                    os.writeObject(selection);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: select:" + ex);
                }

                selWherePane.setVisible(false);
            }
        });
        selWhereGroupByButton.setOnAction(e -> {
            if (conds.size() > 0) {
                for(WhereCondition c : conds) {
                    selection.addCondition(c);
                }

                selGroupByChoiceBoxA.setValue(null);
                selGroupByChoiceBoxA.getItems().clear();

                for (Attribute temp : selection.getTable().getAttributes()) {
                    selGroupByChoiceBoxA.getItems().add(new TableAttribute(selection.getTable().getTableName(), temp.getAttributeName()));
                }
                for (Join j : selection.getJoins()) {
                    for (Attribute temp : j.getTable().getAttributes()) {
                        selGroupByChoiceBoxA.getItems().add(new TableAttribute(j.getTable().getTableName(), temp.getAttributeName()));
                    }
                }

                selWherePane.setVisible(false);
                selGroupByPane.setVisible(true);
            }
        });

        // select - group by
        selGroupByCancelButton.setOnAction(e -> selGroupByPane.setVisible(false));
        selGroupByOkButton.setOnAction(e -> {
            if (selGroupByChoiceBoxA.getValue() != null) {
                selection.setGroupByAttribute(selGroupByChoiceBoxA.getValue());

                // send to the server
                try {
                    os.writeUTF("select");
                    os.flush();
                    os.writeObject(selection);
                    os.flush();
                } catch (Exception ex) {
                    System.out.println("Error! Transmit message: select");
                }

                selGroupByPane.setVisible(false);
            }
        });

        selOutputCancelButton.setOnAction(e -> {
            selOutputPane.getChildren().clear();
            selOutputPane.setVisible(false);
        });
    }


    public void initTreeView() {
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
                    TreeItem<String> attrItem = new TreeItem<>(index.getAttribute());
                    indItem.getChildren().add(attrItem);
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
        ntfkPane1.setVisible(false);
        ntfkPane2.setVisible(false);
        niPane.setVisible(false);
        deletePane.setVisible(false);
        insertPane.setVisible(false);
        selFromPane.setVisible(false);
        selJoinPane.setVisible(false);
        selAttrPane.setVisible(false);
        selWherePane.setVisible(false);
        selGroupByPane.setVisible(false);
        selOutputPane.setVisible(false);
    }

    public void setSelectedRow(ObservableList<String> list) {
        selectedRow = list;
    }

    public void setDatabases(List<Database> list) {
        this.databases = list;
    }

    public void initOutput(String[] list) {
        for (int i = 0; i < list.length - 1; ++i) {
            addOutputColumn(list[i], i);
        }
        showOutputPane();
    }

    public void addOutputColumn(String string, int i) {
        final TableColumn<ObservableList<String>, String> column1 = new TableColumn<>(string);
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(i)));
        selOutputTableView.getColumns().add(column1);
    }

    public void addOutputRow(String value) {
        ObservableList<String> row = FXCollections.observableArrayList();

        String[] list = value.split("#", -1);
        row.addAll(Arrays.asList(list).subList(0, list.length - 1));

        selOutputTableView.getItems().add(row);
    }

    public void showOutputPane() {
        hidePanes();
        selOutputPane.setVisible(true);
    }
}

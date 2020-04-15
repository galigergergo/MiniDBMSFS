package sample;

import data.*;
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

    // selected rows of table views
    private ObservableList<String> selectedRow = FXCollections.observableArrayList();

    private List<Database> databases;
    private String[] attrTypes = {"int", "char", "varchar", "date"};
    private String[] operatorTypes = {"=", "!=", "<", ">", "<=", ">="};
    private String[] uniqueList = {"unique", "not unique"};
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
                        if (!selectedRow.get(0).equals(ntChoiceBoxPK.getValue()) && selectedRow.size() > 0 && ntfkChoiceBoxA.getValue() != null) {
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
                IndexFile newI = new IndexFile(niTextFieldN.getText(), true, niChoiceBoxA.getValue());
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
    }

    public void setSelectedRow(ObservableList<String> list) {
        selectedRow = list;
    }

    public void setDatabases(List<Database> list) {
        this.databases = list;
    }

}

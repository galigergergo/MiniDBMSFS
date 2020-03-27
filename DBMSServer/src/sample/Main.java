package sample;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import data.DataBases;
import data.Database;
import data.IndexFile;
import data.Table;
import org.bson.Document;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        DataBases databases = DataBases.FromJson();

        // hat igy kell felcsatlakozni a MANGO szerverre.
        // de ezt Hudi csinalta a sajat gepen. Nem tom hogy fog mukodni mas gepen.
        // mert ez a Hudi clusterje.
        // User: banditar
        // pass: igen1234
        String connectionString = "mongodb+srv://banditar:igen1234@cluster0-rhgog.mongodb.net/test?retryWrites=true&w=majority";
        com.mongodb.client.MongoClient mongoClients;

        try {
            mongoClients = MongoClients.create(connectionString);
        } catch (Exception e) {
            System.out.println("Error at connecting to the MANGO\n" + e);
            return;
        }
        System.out.println("Connected to MANGO");

/*
        MongoDatabase Ddatabase2 = mongoClients.getDatabase("DBtest2");
        // connecting to Collection from MANGO
        MongoCollection mongoCollection2 = Ddatabase2.getCollection("CollTest2");


        mongoCollection2.insertOne(new Document("_id", 1).append("name", "Meg").append("age", 16)
        .append("race", "white"));
        mongoCollection2.insertOne(new Document("_id", 2).append("name", "Jo").append("age", 15)
                .append("race", "brown"));
        mongoCollection2.insertOne(new Document("_id", 3).append("name", "Beth").append("age", 14)
                .append("race", "white"));
        mongoCollection2.insertOne(new Document("_id", 4).append("name", "Amy").append("age", 12)
                .append("race", "white"));

        String expre = "===";
        String fieldName = "Price";
        String valll = "123";
        switch (expre){
            case "=":
                mongoCollection2.deleteMany(Filters.eq(fieldName, valll));
                break;
            case "!=":
                mongoCollection2.deleteMany(Filters.ne(fieldName, valll));
                break;
            case "<=":
                mongoCollection2.deleteMany(Filters.lte(fieldName, valll));
                break;
            case "<":
                mongoCollection2.deleteMany(Filters.lt(fieldName, valll));
                break;
            case ">=":
                mongoCollection2.deleteMany(Filters.gte(fieldName, valll));
                break;
            case ">":
                mongoCollection2.deleteMany(Filters.gt(fieldName, valll));
                break;
        }
*/


        // establish server
        ServerSocket ss = new ServerSocket(54321);
        Socket s = ss.accept();
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

        System.out.println("Server started...");
        String str = "";
        String dbName;
        String tName;
        String result;

        MongoDatabase mongoDatabase;
        MongoCollection mongoCollection;

        while(!str.equals("stop")) {
            if(is.available() > 0) {
                System.out.println("got");
                str = is.readUTF();
                System.out.println(str);
                switch (str) {
                    case "start":
                        System.out.println("sent");
                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "ndb": // new data base
                        Database newDb = (Database) is.readObject();
                        databases.Databases.add(newDb);

                        // write updated databases to JSON files
                        DataBases.ToJson(databases);

                        System.out.println(databases.Databases);
                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "nt":
                        dbName = is.readUTF();
                        Table newT = (Table) is.readObject();
                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                temp.addTable(newT);
                                break;
                            }
                        }

                        // write updated databases to JSON files
                        DataBases.ToJson(databases);

                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "ni":
                        dbName = is.readUTF();
                        tName = is.readUTF();
                        IndexFile newI = (IndexFile) is.readObject();

                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                for (Table tempT : temp.getTables()) {
                                    if (tempT.getTableName().equals(tName)) {
                                        tempT.addIndexFile(newI);
                                    }
                                    break;
                                }
                                break;
                            }
                        }

                        // write updated databases to JSON files
                        DataBases.ToJson(databases);

                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "ddb":
                        dbName = is.readUTF();
                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                databases.Databases.remove(temp);
                                break;
                            }
                        }

                        // write updated databases to JSON files
                        DataBases.ToJson(databases);

                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "dt":
                        dbName = is.readUTF();
                        tName = is.readUTF();

                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                for (Table tempT : temp.getTables()) {
                                    if (tempT.getTableName().equals(tName)) {
                                        temp.getTables().remove(tempT);
                                    }
                                    break;
                                }
                                break;
                            }
                        }

                        // write updated databases to JSON files
                        DataBases.ToJson(databases);

                        os.writeObject(databases.Databases);      // send updated database list
                        os.flush();
                        break;

                    case "insert":
                        dbName = is.readUTF();
                        tName = is.readUTF();
                        String key = is.readUTF();
                        // attributes to values
                        String attrs = is.readUTF();
                        String value = is.readUTF();

                        // connecting to DataBase from MANGO
                        mongoDatabase = mongoClients.getDatabase(dbName);
                        // connecting to Collection from MANGO
                        mongoCollection = mongoDatabase.getCollection(tName);

                        String[] att = attrs.split("#");
                        String[] val = value.split("#");

                        // creating the Document to insert
                        // key: value pairs
                        // actually: _id: 'key'
                        //           attr: value...
                        Document document = new Document("_id", key);
                        for (int i = 0; i < att.length; i++) {
                            document.append(att[i], val[i]);
                        }

                        try {
                            // inserting to MANGO Collection
                            mongoCollection.insertOne(document);
                            result = "Inserted correctly";
                        } catch (Exception e){
                            // if insert failed. e.g. duplicate key
                            result = "Insert failed";
                        }

                        // hiba/sikeruzenet visszaterites
                        os.writeObject(result);
                        os.flush();
                        break;

                    case "delete":
                        dbName = is.readUTF();
                        tName = is.readUTF();
                        data.Condition condition = (data.Condition) is.readObject();

                        // connecting to DataBase from MANGO
                        mongoDatabase = mongoClients.getDatabase(dbName);
                        // connecting to Collection from MANGO
                        mongoCollection = mongoDatabase.getCollection(tName);

                        String operator = condition.getOperator();
                        String fieldName = condition.getAttribute();
                        String valll = condition.getValue();

                        // check if attribute is PK
                        for(Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                for (Table t : db.getTables()) {
                                    if (t.getTableName().equals(tName)){
                                        if (t.getpKAttrName().equals(fieldName)) {
                                            fieldName = "_id";
                                        }
                                    }
                                }
                            }
                        }

                        Document found;
                        // ha nincs is benne lol
                        // de csak ha '='. mert nem lehet megnezni hogy 'letezik-e record, melyben
                        //                          az evszam >= 1789;
                        //                  Vagyis meglehet. de hosszu es koltseges lol
                        if (operator.equals("=")) {
                            found = (Document) mongoCollection.find(new Document(fieldName, valll)).first();
                            if (found == null) {
                                result = "Record does not exist";
                                os.writeObject(result);
                                os.flush();
                                break;
                            }
                        }

                        switch (operator){
                            case "=":
                                mongoCollection.deleteMany(Filters.eq(fieldName, valll));
                                break;
                            case "!=":
                                mongoCollection.deleteMany(Filters.ne(fieldName, valll));
                                break;
                            case "<=":
                                mongoCollection.deleteMany(Filters.lte(fieldName, valll));
                                break;
                            case "<":
                                mongoCollection.deleteMany(Filters.lt(fieldName, valll));
                                break;
                            case ">=":
                                mongoCollection.deleteMany(Filters.gte(fieldName, valll));
                                break;
                            case ">":
                                mongoCollection.deleteMany(Filters.gt(fieldName, valll));
                                break;
                        }


                        // foreign key null-ra allitas
                        //      majd maskor


                        found = (Document) mongoCollection.find(new Document(fieldName, valll)).first();
                        if(found != null) {
                            result = "Delete failed";
                        } else {
                            result = "Deleted correctly";
                        }
                        // hiba/sikeruzenet visszaterites
                        os.writeObject(result);
                        os.flush();
                        break;
                }
            }
        }
        is.close();
        os.close();
        s.close();
        ss.close();
    }
}

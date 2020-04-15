package sample;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import data.*;
import javafx.scene.control.Tab;
import org.bson.Document;

import javax.print.Doc;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

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
        MongoDatabase Ddatabase2 = mongoClients.getDatabase("MyDb");
        // connecting to Collection from MANGO
        MongoCollection<Document> mongoCollection2 = Ddatabase2.getCollection("Ablakpucolo");

        // iterate through FIND
        FindIterable<Document> doc = mongoCollection2.find(Filters.gte("email", ""));
        for (Document next : doc) {
            System.out.println("doc: " + next);
            System.out.println(next.get("email").getClass());
        }

        /*
        // listing Index names
        ListIndexesIterable<Document> currentIndexes = mongoCollection2.listIndexes();
        MongoCursor<Document> cursor = currentIndexes.iterator();
        while (cursor.hasNext()) {
            Object next = cursor.next();
            String name = ((Document) next).get("name").toString();
            String value = ((Document) next).get("value").toString();
            System.out.println("ListName: " + name);
        }
        */

/*
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
        String delVal = "123";
        switch (expre){
            case "=":
                mongoCollection2.deleteMany(Filters.eq(fieldName, delVal));
                break;
            case "!=":
                mongoCollection2.deleteMany(Filters.ne(fieldName, delVal));
                break;
            case "<=":
                mongoCollection2.deleteMany(Filters.lte(fieldName, delVal));
                break;
            case "<":
                mongoCollection2.deleteMany(Filters.lt(fieldName, delVal));
                break;
            case ">=":
                mongoCollection2.deleteMany(Filters.gte(fieldName, delVal));
                break;
            case ">":
                mongoCollection2.deleteMany(Filters.gt(fieldName, delVal));
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
        MongoCollection<Document> mongoCollection;
        IndexOptions indexOptions;

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

                        // create MANGO database and collection
                        mongoDatabase = mongoClients.getDatabase(dbName);
                        mongoDatabase.createCollection(newT.getTableName());

                        // create index in MANGO for unique and foreign key attributes
                        mongoCollection = mongoDatabase.getCollection(newT.getTableName());
                        indexOptions = new IndexOptions().unique(true);
                        IndexFile index = new IndexFile(newT.getpKAttrName() + "Index", false, newT.getpKAttrName());
                        newT.addIndexFile(index);
                        for (Attribute attr : newT.getAttributes()) {
                            if (!attr.getAttributeName().equals(newT.getpKAttrName())) {
                                if (attr.getIsUnique()) {
                                    indexOptions.unique(true);
                                    mongoCollection.createIndex(Indexes.ascending(attr.getAttributeName()), indexOptions);
                                    index = new IndexFile(attr.getAttributeName() + "Index", true, attr.getAttributeName());
                                    newT.addIndexFile(index);
                                } else {
                                    for (ForeignKey fk : newT.getForeignKeys()) {
                                        if (fk.getAttrName().equals(attr.getAttributeName())) {
                                            indexOptions.unique(false);
                                            mongoCollection.createIndex(Indexes.ascending(attr.getAttributeName()), indexOptions);
                                            index = new IndexFile(attr.getAttributeName() + "Index", false, attr.getAttributeName());
                                            newT.addIndexFile(index);
                                        }
                                    }
                                }
                            }
                        }

                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                temp.addTable(newT);
                                System.out.println(newT.getAttributes().get(0).getIsUnique());
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

                        // creating IndexFile in MANGO
                        // and in our JSON Catalog
                        for (Database temp : databases.Databases) {
                            if (temp.getDataBaseName().equals(dbName)) {
                                mongoDatabase = mongoClients.getDatabase(dbName);
                                for (Table tempT : temp.getTables()) {
                                    if (tempT.getTableName().equals(tName)) {
                                        mongoCollection = mongoDatabase.getCollection(tName);
                                        indexOptions = new IndexOptions();
                                        indexOptions.name(newI.getIndexName());     // its name
                                        indexOptions.unique(newI.isUnique());       // unique

                                        // into MANGO
                                        // in a try catch, because if it already exists: throws Exception
                                        try {
                                            mongoCollection.createIndex(Indexes.ascending(newI.getAttribute()), indexOptions);

                                            // into Catalog
                                            tempT.addIndexFile(newI);
                                        } catch (Exception e) {
                                            System.out.println("Exception at creating Index: " + e);
                                        }

                                        break;
                                    }
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
                                // drop from MANGO
                                mongoClients.getDatabase(dbName).drop();

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
                                        // drop table from MANGO
                                        mongoClients.getDatabase(dbName).getCollection(tName).drop();

                                        temp.getTables().remove(tempT);
                                        break;
                                    }
                                }
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

                        // check if our table is a child
                        // if so, check if the attr to which we are referring exists
                        boolean insertable = true;
                        for (Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                for (Table t : db.getTables()) {
                                    if (t.getTableName().equals(tName)) {
                                        for (ForeignKey fk : t.getForeignKeys()) {
                                            String refT = fk.getRefTableName();
                                            String refAttr = fk.getRefAttrName();
                                            String attr = fk.getAttrName();
                                            String ourVal = (String) document.get(attr);

                                            // check if refAttr is pk
                                            for (Table refTab : db.getTables()) {
                                                if (refTab.getTableName().equals(refT)) {
                                                    if (refTab.getpKAttrName().equals(refAttr)) {
                                                        refAttr = "_id";
                                                    }
                                                }
                                            }

                                            MongoCollection<Document> mongoCollectionFK = mongoDatabase.getCollection(refT);
                                            Document found = mongoCollectionFK.find(Filters.eq(refAttr, ourVal)).first();
                                            if (found == null) {
                                                // doesn't exist. Cant insert

                                                result = "Can't insert into child table; Parent hasn't got your value.";
                                                os.writeObject(result);
                                                os.flush();
                                                insertable = false;
                                                break;
                                            }
                                        }
                                        if (!insertable) {
                                            break;
                                        }
                                    }
                                }
                                if (!insertable) {
                                    break;
                                }
                            }
                        }
                        if (!insertable) {
                            break;
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
                        String delVal = condition.getValue();

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

                        Document found = null;
                        // ha nincs is benne, de csak ha '='
                        if (operator.equals("=")) {
                            found = mongoCollection.find(new Document(fieldName, delVal)).first();
                            if (found == null) {
                                result = "Record does not exist";
                                os.writeObject(result);
                                os.flush();
                                break;
                            }
                        }

                        MongoCollection<Document> mongoCollectionFK;
                        Document docFK = null;
                        boolean child = false;
                        // the table in which a FK is pointing to our attr
                        String refTable = "";
                        // the attr in our table to which the fk is pointing to
                        String refAttr = "";
                        // the attribute which is bound to our attr. Not the fk's refAttr, but fk's attr
                        String attrFk = "";
                        // check if it is a foreign key in another table
                        for (Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                // search through all tables
                                for (Table t : db.getTables()) {
                                    // search through foreign keys
                                    for (ForeignKey fk : t.getForeignKeys()) {
                                        // if one is referring to our Table
                                        if (fk.getRefTableName().equals(tName)){
                                            refTable = t.getTableName();
                                            refAttr = fk.getRefAttrName();
                                            attrFk = fk.getAttrName();

                                            // check if refAttr is PK
                                            for (Table delT : db.getTables()) {
                                                if (delT.getTableName().equals(tName)) {
                                                    if (delT.getpKAttrName().equals(refAttr)) {
                                                        refAttr = "_id";
                                                    }
                                                }
                                            }

                                            mongoCollectionFK = mongoDatabase.getCollection(refTable);

                                            // means that we are a Parent
                                            // list all entries that should be deleted
                                            // check for all of them if in the referrer table there is an attr
                                            // that points to our attr

                                            // all documents that fulfill our condition
                                            FindIterable<Document> delDocs = null;
                                            switch (operator){
                                                case "=":
                                                    delDocs = mongoCollection.find(Filters.eq(fieldName, delVal));
                                                    break;
                                                case "!=":
                                                    delDocs = mongoCollection.find(Filters.ne(fieldName, delVal));
                                                    break;
                                                case "<=":
                                                    delDocs = mongoCollection.find(Filters.lte(fieldName, delVal));
                                                    break;
                                                case "<":
                                                    delDocs = mongoCollection.find(Filters.lt(fieldName, delVal));
                                                    break;
                                                case ">=":
                                                    delDocs = mongoCollection.find(Filters.gte(fieldName, delVal));
                                                    break;
                                                case ">":
                                                    delDocs = mongoCollection.find(Filters.gt(fieldName, delVal));
                                                    break;
                                            }
                                            for (Document next : delDocs) {

                                                String refValInDel = (String) next.get(refAttr);
                                                // we search for this in the referrer table
                                                docFK = mongoCollectionFK.find(Filters.eq(attrFk, refValInDel)).first();
                                                if (docFK != null) {
                                                    // it means it exists >> it is a Child of our table
                                                    result = "Cannot delete, because it is a Parent to an existing Child";
                                                    os.writeObject(result);
                                                    os.flush();

                                                    child = true;
                                                    break;
                                                }
                                            }
                                            if (child) {
                                                // it was found already
                                                break;
                                            }
                                        }
                                    }
                                    if (child) {
                                        // it was found already
                                        break;
                                    }
                                }
                                if (child) {
                                    // it was found already
                                    break;
                                }
                            }
                        }
                        if (child) {
                            // it was found already
                            break;
                        }

// AND THEN THE INSERT INTO CHILD

                        switch (operator){
                            case "=":
                                mongoCollection.deleteMany(Filters.eq(fieldName, delVal));
                                break;
                            case "!=":
                                mongoCollection.deleteMany(Filters.ne(fieldName, delVal));
                                break;
                            case "<=":
                                mongoCollection.deleteMany(Filters.lte(fieldName, delVal));
                                break;
                            case "<":
                                mongoCollection.deleteMany(Filters.lt(fieldName, delVal));
                                break;
                            case ">=":
                                mongoCollection.deleteMany(Filters.gte(fieldName, delVal));
                                break;
                            case ">":
                                mongoCollection.deleteMany(Filters.gt(fieldName, delVal));
                                break;
                        }


                        result = "Deleted correctly";
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

package sample;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import data.*;
import org.bson.Document;

import javax.print.Doc;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        DataBases databases = DataBases.FromJson();

        // hat igy kell felcsatlakozni a MANGO szerverre.
        // de ezt Hudi csinalta a sajat gepen. Nem tom hogy fog mukodni mas gepen.
        // mert ez a Hudi clusterje.
        // User: banditar
        // pass: igen1234
        //String connectionString = "mongodb+srv://banditar:igen1234@cluster0-rhgog.mongodb.net/test?retryWrites=true&w=majority";
        String connectionString = "mongodb://banditar:igen1234@cluster0-shard-00-00-rhgog.mongodb.net:27017,cluster0-shard-00-01-rhgog.mongodb.net:27017,cluster0-shard-00-02-rhgog.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";
        com.mongodb.client.MongoClient mongoClients;

        try {
            mongoClients = MongoClients.create(connectionString);
        } catch (Exception e) {
            System.out.println("Error at connecting to the MANGO\n" + e);
            return;
        }
        System.out.println("Connected to MANGO");

        // // beszuras 100 000 sort a costumers tablaba
        // // Name, Age, Email Address, Category ID
//        MongoDatabase Ddatabase2 = mongoClients.getDatabase("MyDb");
//        // connecting to Collection from MANGO
//        MongoCollection<Document> mongoCollection2 = Ddatabase2.getCollection("costumers");
////
//        FileWriter myWriter = new FileWriter("sorted_thousand_rows.txt");

// 100 000 sor kiirasa
//        FindIterable<Document> docx = mongoCollection2.find();
//        int N = 200000;
//        int iu = 0;
//        String[] thousand = new String[N];
//        for (Document next : docx) {
////            System.out.println(next);
////            myWriter.write(next + "\n");
//            thousand[iu] = next.toString();
//            iu++;
//            thousand[iu] = next.toString();
//            iu++;
//        }
// SORT AND UNIQUE ARRAY LIST
//        Arrays.sort(thousand);
//        String[] unique = Arrays.stream(thousand).distinct().toArray(String[]::new);
//        for (String str : unique) {
//            myWriter.write(str + "\n");
//        }
//        myWriter.close();
//
//        final java.util.Random rand = new java.util.Random();
//        final String lexicon = "qwertyuioplkjhgfdsazxcvbnm";
//
//        int N = 100000;
//        for (int i = 0; i < N; i++) {
//            StringBuilder attrs = new StringBuilder();
//            StringBuilder builder = new StringBuilder();
//
//            // name
//            int length = rand.nextInt(5)+3;
//            for(int j = 0; j < length; j++) {
//                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
//            }
//            String Name = builder.toString();
//
//            // age
//            int Age = rand.nextInt(95) + 5;
//
//            // email
//            if (rand.nextInt(10) <= 3) {
//                builder = new StringBuilder("test@nomail.com");
//            } else {
//                final String lexiconemail = "qwertyuioplkjhgfdsazxcvbnm01234567890-_.";
//                builder = new StringBuilder();
//                length = rand.nextInt(3) + 4;
//                for (int j = 0; j < length; j++) {
//                    builder.append(lexiconemail.charAt(rand.nextInt(lexiconemail.length())));
//                }
//                builder.append('@');
//                String[] emails = {"yahoo.com", "yahoo.ro", "yahoo.ru", "yahoo.hu", "gmail.com", "freemail.hu", "citromail.hu", "scs.ubbcluj.ro", "math.ubbcluj.ro", "cs.ubbcluj.ro"};
//                int index = rand.nextInt(emails.length);
//                builder.append(emails[index]);
//            }
//            String Email = builder.toString();
//
//            // category
//            int Category = rand.nextInt(50) + 1;
//
//
//            attrs.append(Name).append('#').append(Age).append('#').append(Email).append('#').append(Category).append('#');
//            try {
//                mongoCollection2.insertOne(new Document("_id", Integer.toString(i)).append("attrs", attrs.toString()));
//            } catch (Exception e) {
//                System.out.println("i = " + i + ": " + e);
//            }
//        }
/*


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
                        IndexFile index = new IndexFile(newT.getpKAttrName() + "Index", false, newT.getpKAttrName());
                        newT.addIndexFile(index);
                        for (Attribute attr : newT.getAttributes()) {
                            if (!attr.getAttributeName().equals(newT.getpKAttrName())) {
                                if (attr.getIsUnique()) {
                                    index = new IndexFile(newT.getTableName() + "_" + attr.getAttributeName() + "_" + "Index", true, attr.getAttributeName());
                                    newT.addIndexFile(index);
                                } else {
                                    for (ForeignKey fk : newT.getForeignKeys()) {
                                        if (fk.getAttrName().equals(attr.getAttributeName())) {
                                            index = new IndexFile(newT.getTableName() + "_" + attr.getAttributeName() + "_" + "Index", false, attr.getAttributeName());
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
                                for (Table tempT : temp.getTables()) {
                                    if (tempT.getTableName().equals(tName)) {
                                        // into Catalog
                                        tempT.addIndexFile(newI);

                                        // insert all values into Mango
                                        mongoDatabase = mongoClients.getDatabase(dbName);
                                        mongoCollection = mongoDatabase.getCollection(tName);
                                        MongoCollection<Document> mongoCollectionIndex = mongoDatabase.getCollection(newI.getIndexName());

                                        String attributes;
                                        String[] attrList;
                                        int i = -1;
                                        FindIterable<Document> doc = mongoCollection.find();
                                        for (Document next : doc) {
                                            boolean found = false;
                                            if (!newI.isUnique()) {
                                                // check if already contains
                                                attributes = (String) next.get("attrs");
                                                attrList = attributes.split("#", -1);
                                                i = getValueIndex(tempT, newI.getAttribute());
                                                FindIterable<Document> indexFile = mongoCollectionIndex.find(Filters.eq("_id", attrList[i]));
                                                for (Document next2 : indexFile) {
                                                    mongoCollectionIndex.deleteOne(next2);
                                                    next2.replace("main_id", next2.get("main_id") + "#" + next.get("_id"));
                                                    mongoCollectionIndex.insertOne(next2);
                                                    found = true;
                                                    break;
                                                }
                                                if (!found) {
                                                    attributes = (String) next.get("attrs");
                                                    attrList = attributes.split("#", -1);
                                                    i = getValueIndex(tempT, newI.getAttribute());
                                                    Document entry = new Document("_id", attrList[i]);
                                                    entry.append("main_id", next.get("_id"));
                                                    mongoCollectionIndex.insertOne(entry);
                                                }
                                            } else {
                                                attributes = (String) next.get("attrs");
                                                attrList = attributes.split("#", -1);
                                                getValueIndex(tempT, newI.getAttribute());
                                                Document entry = new Document("_id", attrList[i]);
                                                entry.append("main_id", next.get("_id"));
                                                mongoCollectionIndex.insertOne(entry);
                                            }
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

                                        // here we should delete its index collections too from MANGO
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

                        String[] att = attrs.split("#", -1);
                        String[] val = value.split("#", -1);

                        // creating the Document to insert
                        // value#value#value#...# string
                        Document document = new Document("_id", key);
                        document.append("attrs", value);

                        // check if our table is a child
                        // if so, check if the attr to which we are referring exists
                        Table currentTable = null;
                        boolean insertable = true;
                        for (Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                for (Table t : db.getTables()) {
                                    if (t.getTableName().equals(tName)) {
                                        currentTable = t;
                                        for (ForeignKey fk : t.getForeignKeys()) {
                                            String refT = fk.getRefTableName();
                                            String refAttr = fk.getRefAttrName();
                                            String attr = fk.getAttrName();
                                            String ourVal = null;
                                            ArrayList<String> attrList = new ArrayList<>();
                                            for (Attribute at : t.getAttributes()) {
                                                attrList.add(at.getAttributeName());
                                            }
                                            int i = 0;
                                            boolean pkFound = false;
                                            while (i < attrList.size() && !attrList.get(i).equals(attr)) {
                                                if (currentTable.getpKAttrName().equals(attrList.get(i))) {
                                                    pkFound = true;
                                                }
                                                i++;
                                            }
                                            if (pkFound) {
                                                i--;
                                            }
                                            if (i < attrList.size()) {
                                                ourVal =  val[i];
                                            }
                                            if (ourVal == null) {
                                                break;
                                            }

                                            // check if refAttr is pk
                                            Table referenceT = null;
                                            for (Table refTab : db.getTables()) {
                                                if (refTab.getTableName().equals(refT)) {
                                                    referenceT = refTab;
                                                    if (refTab.getpKAttrName().equals(refAttr)) {
                                                        refAttr = "_id";
                                                    }
                                                }
                                            }

                                            MongoCollection<Document> mongoCollectionFK = mongoDatabase.getCollection(refT);
                                            Document found = null;
                                            ArrayList<Document> found2 = null;
                                            if (refAttr.equals("_id")) {
                                                found = mongoCollectionFK.find(Filters.eq("_id", ourVal)).first();
                                            }
                                            else {
                                                found2 = findElements(referenceT, mongoCollectionFK, new Condition(refAttr, "=", ourVal));
                                            }
                                            if ((refAttr.equals("_id") && found == null) || (!refAttr.equals("_id") && found2 == null)) {
                                                // doesn't exist. Cant insert
                                                result = "Can't insert into parent table; Child hasn't got your value.";
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
                            // inserting into MANGO Index Collection
                            for (int i = 0; i < att.length; i++) {
                                if (!val[i].equals("")) {
                                    for (IndexFile ind : currentTable.getIndexFiles()) {
                                        if (ind.getAttribute().equals(att[i])) {
                                            MongoCollection<Document> mongoCollectionIndex = mongoDatabase.getCollection(ind.getIndexName());

                                            boolean found = false;
                                            if (!ind.isUnique()) {
                                                // check if already contains
                                                FindIterable<Document> indexFile = mongoCollectionIndex.find(Filters.eq("_id", val[i]));
                                                for (Document next2 : indexFile) {
                                                    mongoCollectionIndex.deleteOne(next2);
                                                    next2.replace("main_id", next2.get("main_id") + "#" + document.get("_id"));
                                                    mongoCollectionIndex.insertOne(next2);
                                                    found = true;
                                                    break;
                                                }
                                                if (!found) {
                                                    Document entry = new Document("_id", val[i]);
                                                    entry.append("main_id", document.get("_id"));
                                                    mongoCollectionIndex.insertOne(entry);
                                                }
                                            } else {
                                                Document entry = new Document("_id", val[i]);

                                                // IF DOESNT EXIST YET
                                                if (mongoCollectionIndex.find(entry).first() == null) {
                                                    entry.append("main_id", document.get("_id"));
                                                    mongoCollectionIndex.insertOne(entry);
                                                } else {
                                                    // NEEDS TO BE UNIQUE
                                                    throw new Exception("Key Already Exists");
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // inserting to MANGO Collection
                            mongoCollection.insertOne(document);
                            result = "Inserted correctly";
                        } catch (Exception e){
                            // if insert failed. e.g. duplicate key
                            result = "Insert failed\n" + e;
                        }

                        // hiba/sikeruzenet visszaterites
                        os.writeObject(result);
                        os.flush();
                        break;

                    case "delete":
                        // check if deletable. ie. is it parent to a child?
                        // delete: from index files
                        // delete: from collection

                        dbName = is.readUTF();
                        tName = is.readUTF();
                        data.Condition condition = (data.Condition) is.readObject();

                        // connecting to DataBase from MANGO
                        mongoDatabase = mongoClients.getDatabase(dbName);
                        // connecting to Collection from MANGO
                        mongoCollection = mongoDatabase.getCollection(tName);

                        Table T = null;
                        String operator = condition.getOperator();
                        String fieldName = condition.getAttribute();
                        String delVal = condition.getValue();

                        // check if attribute is PK
                        for(Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                for (Table t : db.getTables()) {
                                    if (t.getTableName().equals(tName)) {
                                        T = t;
                                        if (t.getpKAttrName().equals(fieldName)) {
                                            fieldName = "_id";
                                        }
                                    }
                                }
                            }
                        }

                        String attributes = "";
                        String[] attrList = null;
                        int indexx = -1;

                        MongoCollection<Document> mongoCollectionIndex;
                        Document docFK;
                        // all documents that fulfill our condition
                        ArrayList<Document> delDocs = null;
                        // deletable docs
                        delDocs = findElements(T, mongoCollection, condition);
                        System.out.println(T.getTableName());
                        boolean child = false;
                        // the table in which a FK is pointing to our attr
                        String refTable = "";
                        // the attr in our table to which the fk is pointing to
                        String refAttr = "";
                        // the child attribute which is bound to our attr
                        String attrFk = "";
                        // the indexname from the child table
                        String childIndex = "";

                        Table delTable = null;

                        // check if it is a foreign key in another table
                        for (Database db : databases.Databases) {
                            if (db.getDataBaseName().equals(dbName)) {
                                for (Table delT : db.getTables()) {
                                    if (delT.getTableName().equals(tName)) {
                                        delTable = delT;
                                    }
                                }

                                // search through all tables
                                for (Table t : db.getTables()) {
                                    // search through foreign keys
                                    for (ForeignKey fk : t.getForeignKeys()) {
                                        // if one is referring to our Table
                                        if (fk.getRefTableName().equals(tName)){
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

                                            // means that we are a Parent
                                            // list all entries that should be deleted
                                            // check for all of them if in the child table there is an attr
                                            // that points to our attr

                                            if (delDocs != null) {
                                                for (Document next : delDocs) {
                                                    // the index file in child
                                                    for (IndexFile ind : t.getIndexFiles()) {
                                                        if (ind.getAttribute().equals(attrFk) && !ind.isUnique()) {
                                                            // the correct index
                                                            childIndex = ind.getIndexName();
                                                            mongoCollectionIndex = mongoDatabase.getCollection(childIndex);

                                                            attributes = (String) next.get("attrs");
                                                            attrList = attributes.split("#", -1);
                                                            indexx = getValueIndex(delTable, refAttr);
                                                            String refValInDel = "";
                                                            if (refAttr.equals("_id")) {
                                                                refValInDel = (String) next.get(refAttr);
                                                            } else {
                                                                refValInDel = attrList[indexx];
                                                            }
                                                            // we search for this in the referrer table INDEX FILE
                                                            docFK = mongoCollectionIndex.find(new Document("_id", refValInDel)).first();
                                                            if (docFK != null) {
                                                                // it means it exists >> it is a Child of our table
                                                                result = "Cannot delete, because it is a Parent to an existing Child";
                                                                os.writeObject(result);
                                                                os.flush();

                                                                child = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (child) {
                                                    // it was found already
                                                    break;
                                                }
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

// AND THEN THE INSERT INTO CHILD-- what dis?
                        // the DELETE
                        try {
                            // delete from index
                            // for all docs, search in index files, and update
                            if (delDocs != null) {
                                for (Document next : delDocs) {
                                    if (T.getIndexFiles() != null) {
                                        int i = 0;
                                        for (IndexFile ind : T.getIndexFiles()) {
                                            i++;
                                            if (i == 1) {
                                                // skip the PK index. in which we don't delete
                                                continue;
                                            }

                                            mongoCollectionIndex = mongoDatabase.getCollection(ind.getIndexName());

                                            String attr = ind.getAttribute();
                                            // check if attr is PK
                                            if (T.getpKAttrName().equals(attr)) {
                                                attr = "_id";
                                            }
                                            attributes = (String) next.get("attrs");
                                            attrList = attributes.split("#", -1);
                                            indexx = getValueIndex(delTable, attr);
                                            String valu = "";
                                            if (attr.equals("_id")) {
                                                valu = (String) next.get(attr);
                                            } else {
                                                valu = attrList[indexx];
                                            }

                                            if (ind.isUnique()) {
                                                mongoCollectionIndex.deleteOne(new Document("_id", valu));
                                            } else {
                                                // delete the attr from the '#'
                                                Document doc = mongoCollectionIndex.find(new Document("_id", valu)).first();
                                                mongoCollectionIndex.deleteOne(doc);
                                                String main = (String) doc.get("main_id");
                                                String[] mains = main.split("#");
                                                main = "";
                                                for (String indexString : mains) {
                                                    if (!indexString.equals(next.get("_id"))) {
                                                        main = main.concat(indexString).concat("#");
                                                    }
                                                }
                                                if (main.length() > 0) {
                                                    main = main.substring(0, main.length() - 1);
                                                    doc.replace("main_id", main);
                                                    mongoCollectionIndex.insertOne(doc);
                                                } else {
                                                    result = "Record does not exist";
                                                    os.writeObject(result);
                                                    os.flush();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    // delete from table
                                    mongoCollection.deleteOne(next);
                                }
                            }

                            result = "Deleted correctly";
                        } catch (Exception e) {
                            System.out.println("Delete Except: " + e);
                            result = "Delete failed. sorry :(";
                        }
                        // hiba/sikeruzenet visszaterites
                        os.writeObject(result);
                        os.flush();
                        break;
                    case "select":
                        Selection selection = (Selection) is.readObject();
                        System.out.println(selection.getDatabase());

                        mongoDatabase = mongoClients.getDatabase(selection.getDatabase());
                        mongoCollection = mongoDatabase.getCollection(selection.getTable().getTableName());

                        FindIterable test = mongoCollection.find();

                        ArrayList<Document> selectedDocs = iterableToList(test);
                        // szelekcio, metszet, rendezes, duplikatumoktol megszabadulas -> selectedDocs



                        // projection
                        ArrayList<String> toSend = projection(selection, selectedDocs, databases);

                        // send data
                        for (String output : toSend) {
                            os.writeObject(output);
                            os.flush();
                        }
                        os.writeObject("over");
                        os.flush();

                        /*
                        os.writeObject("attr#attr2#");
                        os.flush();
                        os.writeObject("val1#val2#");
                        os.flush();
                        os.writeObject("val3#val4#");
                        os.flush();
                        os.writeObject("over");
                        os.flush();
                         */

                        break;
                }
            }
        }
        is.close();
        os.close();
        s.close();
        ss.close();
    }

    // convert FindIterable to ArrayList
    public static ArrayList<Document> iterableToList(FindIterable<Document> iterable) {
        ArrayList<Document> result = new ArrayList<>();

        for (Document doc : iterable) {
            result.add(doc);
        }

        return result;
    }

    // projection operator
    // returns ArrayList of Strings to be sent for client
    public static ArrayList<String> projection(Selection selection, ArrayList<Document> docs, DataBases databases) {
        ArrayList<String> result = new ArrayList<>();

        // add first row to result, row of attribute names
        String row = "";
        for (TableAttribute attr : selection.getAttributes()) {
            row += attr.getTableName() + " " + attr.getAttributeName() + "#";
        }
        result.add(row);

        // get tables of the selection attributes
        ArrayList<Table> tables = new ArrayList<>();
        for (TableAttribute attr : selection.getAttributes()) {
            tables.add(findTable(databases, selection.getDatabase(), attr.getTableName()));
        }

        // check if there is index on every attribute
        boolean eachHasIndex = true;
        ArrayList<String> indexFiles = new ArrayList<>();
        int i = 0;
        for (TableAttribute attr : selection.getAttributes()) {
            IndexFile indexFile = findIndexOnAttribute(tables.get(i), attr.getAttributeName());
            i++;
            if (indexFile == null) {
                eachHasIndex = false;
                break;
            } else {
                indexFiles.add(indexFile.getIndexName());
            }
        }

        if (eachHasIndex) {
            // do something ..

        } else {
            // get dollection indeces of variables
            int[] indeces = new int[tables.size()];
            i = 0;
            for (TableAttribute attr : selection.getAttributes()) {
                indeces[i] = (getValueIndex(tables.get(i), attr.getAttributeName()));
                i++;
            }

            // insert strings into output
            for (Document doc : docs) {
                row = "";
                for (int j = 0; j < tables.size(); j++) {
                    if (indeces[j] == -1) {
                        row += (String) doc.get("_id");
                    } else {
                        String attrs = (String) doc.get("attrs");
                        row += attrs.split("#", -1)[indeces[j]];
                    }
                    row += "#";
                }
                result.add(row);
            }
        }

        return result;
    }

    // check if there is index file for an attribute in a table
    public static IndexFile findIndexOnAttribute(Table table, String attribute) {
        for (IndexFile index : table.getIndexFiles()) {
            if (index.getAttribute().equals(attribute)) {
                return index;
            }
        }
        return null;
    }

    // returns Table from database list
    public static Table findTable(DataBases databases, String databaseName, String tableName) {
        for (Database db : databases.Databases) {
            if (db.getDataBaseName().equals(databaseName)) {
                for (Table t : db.getTables()) {
                    if (t.getTableName().equals(tableName)) {
                        return t;
                    }
                }
            }
        }
        return null;
    }

    // get index of a value in attrs field of collection
    public static int getValueIndex(Table table, String attributeName) {
        // if pk return -1
        if (table.getpKAttrName().equals(attributeName)) {
            return -1;
        }

        ArrayList<Attribute> attrList = table.getAttributes();
        int i = 0;
        boolean pkFound = false;
        while (i < attrList.size() && !attrList.get(i).getAttributeName().equals(attributeName)) {
            if (attrList.get(i).getAttributeName().equals(table.getpKAttrName())) {
                pkFound = true;
            }
            i++;
        }
        if (i >= attrList.size()) {
            return -1;
        }
        if (pkFound) {
            i--;        // pk not in the attrs field
        }

        return i;
    }

    // finds all entries in a table based on a condition
    public static ArrayList<Document> findElements(Table table, MongoCollection<Document> collection, Condition condition) {
        ArrayList<Document> list = new ArrayList<>();

        // if PK
        if (table.getpKAttrName().equals(condition.getAttribute())) {
            FindIterable<Document> result = null;
            switch (condition.getOperator()) {
                case "=":
                    result = collection.find(Filters.eq("_id", condition.getValue()));
                    break;
                case "!=":
                    result = collection.find(Filters.ne("_id", condition.getValue()));
                    break;
                case "<=":
                    result = collection.find(Filters.lte("_id", condition.getValue()));
                    break;
                case "<":
                    result = collection.find(Filters.lt("_id", condition.getValue()));
                    break;
                case ">=":
                    result = collection.find(Filters.gte("_id", condition.getValue()));
                    break;
                case ">":
                    result = collection.find(Filters.gt("_id", condition.getValue()));
                    break;
            }
            if (result == null) {
                return null;
            }
            for (Document doc : result) {
                list.add(doc);
            }
            return list;
        }

        // get index of the value in condition
        int i = getValueIndex(table, condition.getAttribute());

        FindIterable<Document> docs = collection.find();
        for (Document iterator : docs) {
            String attributes = (String) iterator.get("attrs");
            String[] values = attributes.split("#", -1);

            int num = 0;
            int condNum = 0;
            switch (condition.getOperator()) {
                case "=":
                    if (values[i].equals(condition.getValue())) {
                        list.add(iterator);
                    }
                    break;
                case "!=":
                    if (!values[i].equals(condition.getValue())) {
                        list.add(iterator);
                    }
                    break;
                case "<=":
                    num = Integer.parseInt(values[i]);
                    condNum = Integer.parseInt(condition.getValue());
                    if (num <= condNum) {
                        list.add(iterator);
                    }
                    break;
                case "<":
                    num = Integer.parseInt(values[i]);
                    condNum = Integer.parseInt(condition.getValue());
                    if (num < condNum) {
                        list.add(iterator);
                    }
                    break;
                case ">=":
                    num = Integer.parseInt(values[i]);
                    condNum = Integer.parseInt(condition.getValue());
                    if (num >= condNum) {
                        list.add(iterator);
                    }
                    break;
                case ">":
                    num = Integer.parseInt(values[i]);
                    condNum = Integer.parseInt(condition.getValue());
                    if (num > condNum) {
                        list.add(iterator);
                    }
                    break;
            }
        }

        if (list.size() == 0) {
            return null;
        }

        return list;
    }
}
package sample;

import data.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<Database> databases;

        // test databases ArrayList
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

        // establish server
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

        System.out.println("Server started...");
        String str = "";
        String dbName;
        String tName;
        while(!str.equals("stop")){
            str = din.readUTF();
            switch(str) {
                case "ndb": // new data base
                    Database newDb = (Database) is.readObject();
                    databases.add(newDb);
                    // mongoDB es JSON varazslat ...
                    os.writeObject(databases);      // send updated database list

                case "nt":
                    dbName = din.readUTF();
                    Table newT = (Table) is.readObject();
                    for(Database temp : databases) {
                        if(temp.getDataBaseName().equals(dbName)) {
                            temp.addTable(newT);
                            break;
                        }
                    }
                    // mongoDB es JSON varazslat ...
                    os.writeObject(databases);      // send updated database list

                case "ni":
                    dbName = din.readUTF();
                    tName = din.readUTF();
                    IndexFile newI = (IndexFile) is.readObject();

                    for(Database temp : databases) {
                        if(temp.getDataBaseName().equals(dbName)) {
                            for(Table tempT : temp.getTables()) {
                                if(tempT.getTableName().equals(tName)) {
                                    tempT.addIndexFile(newI);
                                }
                                break;
                            }
                            break;
                        }
                    }
                    // mongoDB es JSON varazslat ...
                    os.writeObject(databases);      // send updated database list

                case "ddb":
                    dbName = din.readUTF();
                    for(Database temp : databases) {
                        if(temp.getDataBaseName().equals(dbName)) {
                            databases.remove(temp);
                            break;
                        }
                    }
                    // mongoDB es JSON varazslat ...
                    os.writeObject(databases);      // send updated database list

                case "dt":
                    dbName = din.readUTF();
                    tName = din.readUTF();
                    for(Database temp : databases) {
                        if(temp.getDataBaseName().equals(dbName)) {
                            for(Table tempT : temp.getTables()) {
                                if(tempT.getTableName().equals(tName)) {
                                    temp.getTables().remove(tempT);
                                }
                                break;
                            }
                            break;
                        }
                    }
                    // mongoDB es JSON varazslat ...
                    os.writeObject(databases);      // send updated database list

            }
        }
        din.close();
        s.close();
        ss.close();
    }
}

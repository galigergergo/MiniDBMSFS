package sample;

import data.Database;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class MySocketListener implements Runnable {

    private Controller controller;
    private ObjectInputStream is;
    private List<Database> databases;

    public MySocketListener(Controller controller, ObjectInputStream is) {
        this.controller = controller;
        this.is = is;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Object obj = is.readObject();
                if(obj instanceof String) {
                    System.out.println((String) obj);
                }
                else if(obj instanceof List) {
                    databases = (List<Database>) obj;
                    System.out.println("received");
                    System.out.println(databases);
                    controller.setDatabases(databases);
                    //databases.clear();
                }
            } catch (Exception ex) {
                System.out.println(ex);
                break;
            }
        }
    }
}

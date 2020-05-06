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
        boolean first = true;
        boolean over = false;
        while(true) {
            try {
                Object obj = is.readObject();
                if(obj instanceof String) {
                    String read = (String) obj;
                    if (read.equals("over")) {
                        over = true;
                    }
                    if (!over && first) {
                        String[] list = read.split("#", -1);
                        for (int i = 0; i < list.length - 1; ++i) {
                            controller.addOutputColumn(list[i], i);
                        }
                        controller.showOutputPane();
                        first = false;
                    } else if (!over) {
                        controller.addOutputRow(read);
                    }
                    else {
                        first = true;
                        over = false;
                    }
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

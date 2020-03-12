package sample;

import data.Database;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MySocketListener implements Runnable {

    private Socket clientSocket;
    private Controller controller;

    public MySocketListener(Controller controller, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.controller = controller;
    }

    @Override
    public void run() {
        try{
            DataInputStream din = new DataInputStream(clientSocket.getInputStream());
            ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
            ArrayList<Database> databases = (ArrayList<Database>) is.readObject();
            controller.setDatabases(databases);
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}

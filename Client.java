// Janosi Jozsef-Hunor
// 522 csoport
// jjim182

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        host = InetAddress.getByName("127.0.0.1");
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            line = br.readLine();
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(line);

            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);
            //close resources
            ois.close();
            oos.close();
        //szia hudi
    }
}

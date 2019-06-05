
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public User user = null;
    public Socket socket = null;
    public void clientStart(User user) {
        this.user=user;
        DataOutputStream output = null;
        try {
            socket = new Socket("localhost",8888);
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(user.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User getUser(){
        return user;
    }
    public void upload(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        System.out.println("HELLO");
        out.writeUTF("UPLOAD");
        System.out.println("HELLO");
        out.writeUTF(path);
        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        in.close();
        out.close();
    }
    public void download(){

    }
    public void list(){
        System.out.println(user.files);
    }
    public void logOut(){
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("END");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

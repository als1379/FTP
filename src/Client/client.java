package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public Socket socket = null;
    public void clientStart(User user) {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        DataOutputStream output = null;
        try {
            socket = new Socket("localhost",8888);
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(name);
            name = in.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void upload(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
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

    }
}

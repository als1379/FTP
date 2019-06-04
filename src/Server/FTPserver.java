package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FTPserver {
    public ArrayList<ClientHandler> handlers = new ArrayList<>();
    public void startServer() {
        System.out.println("Hello");
        try {
            ServerSocket socket = new ServerSocket(8888);
            while (true) {
                System.out.println("wait");
                Socket client = socket.accept();
                System.out.println("nowait");
                ClientHandler handler = new ClientHandler(client);
                handlers.add(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class ClientHandler extends Thread{
        private DataInputStream input;
        private DataOutputStream output;

        public ClientHandler(Socket client)  {
            try {
                File file = new File("ali.xml");
                InputStream in = client.getInputStream();
                OutputStream out = new FileOutputStream(file);
                byte[] bytes = new byte[16*1024];

                int count;
                while ((count = in.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }

                out.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {

        }
    }
}

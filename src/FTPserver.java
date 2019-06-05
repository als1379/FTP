import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FTPserver {
    public ArrayList<ClientHandler> handlers = new ArrayList<>();
    public void startServer() {
        try {
            ServerSocket socket = new ServerSocket(8888);
            while (true) {
                System.out.println("wait for Client");
                Socket client = socket.accept();
                System.out.println("Client accepted");
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
                input=new DataInputStream(client.getInputStream());
                output=new DataOutputStream(client.getOutputStream());
                String name = input.readUTF();
                System.out.println("Client name: "+ name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run(){
            try {
                while (true){
                    String todo = input.readUTF();
                    if(todo.equals("END")){
                        handlers.remove(this);
                        input.close();
                        output.close();
                        return;
                    }
                    if(todo.equals("UPLOAD")){

                    }
                    if(todo.equals("DOWNLOAD")){

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

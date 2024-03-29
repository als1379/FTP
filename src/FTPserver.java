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
        private FileOutputStream outFile;
        private FileInputStream inFile;

        public ClientHandler(Socket client)  {
            try {
                input=new DataInputStream(client.getInputStream());
                output=new DataOutputStream(client.getOutputStream());
                String name = input.readUTF();
                System.out.println("Client name: "+ name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            start();
        }
        @Override
        public void run(){
            try {
                while (true) {
                    String todo = input.readUTF();
                    System.out.println(todo);
                    if (todo.equals("END")) {
                        handlers.remove(this);
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    if (todo.equals("UPLOAD")) {
                        try {
                            String path = input.readUTF();
                            System.out.println(path);
                            outFile = new FileOutputStream(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        byte[] bytes = new byte[16 * 1024];
                        int count = 16384;
                        while (count >= 16384) {
                            try {
                                count = input.read(bytes);
                                outFile.write(bytes, 0, count);
                                System.out.println(count);
                            } catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    }
                    if (todo.equals("DOWNLOAD")) {
                        byte[] bytes = new byte[16 * 1024];
                        try {
                            String name = input.readUTF();
                            String fileName = input.readUTF();
                            inFile = new FileInputStream("./UserFiles/"+name+"/"+fileName);
                            System.out.println("./"+name+"/"+fileName);
                            int count;
                            while ((count = inFile.read(bytes)) > 0) {
                                output.write(bytes, 0, count);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

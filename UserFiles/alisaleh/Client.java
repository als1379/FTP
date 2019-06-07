
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
        String fileName = nameOfFile(path);
        System.out.println(fileName);
        boolean bigflag = false;
        while (true) {
            File dir = new File("./UserFiles/" + user.getUsername());
            File[] files = dir.listFiles();
            boolean flag = false;
            if (files != null) {
                for (File file : files) {
                    if(file.getName().equals(fileName)){
                        if(!bigflag) {
                            System.out.println("1-Replace");
                            System.out.println("2-Keep");
                            System.out.println("3-Cancel");
                            Scanner sc = new Scanner(System.in);
                            String todo = sc.nextLine();
                            switch (todo) {
                                case "1":
                                    break;
                                case "2":
                                    bigflag = true;
                                    flag = true;
                                    fileName = fileName + "-Duplicate";
                                    break;
                                case "3":
                                    return;
                                default:
                                    System.out.println("Ivalid command");
                            }
                        }
                        else{
                            fileName = fileName + "-Duplicate";
                        }
                    }
                }
            }
            if(!flag)
                break;
        }
        File file = new File(path);
        byte[] bytes = new byte[16 * 1024];
        try {
            InputStream in = new FileInputStream(file);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("UPLOAD");
            out.writeUTF("./UserFiles/" + user.getUsername() + "/" + fileName + "/");
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public void download(String fileName) throws IOException{
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("DOWNLOAD");
            out.writeUTF(user.getUsername());
            out.writeUTF(fileName);
        } catch (IOException e){
            e.printStackTrace();
        }
        OutputStream outFile = null;
        String home = System.getProperty("user.home");
        File file = new File(home+"/Downloads/" + fileName);
        try {
            outFile = new FileOutputStream(home+"/Downloads/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream input = null;
        try {
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[16 * 1024];
        int count = 16384;
        while (count >= 16384) {
            try {
                count = input.read(bytes);
                outFile.write(bytes, 0, count);
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        System.out.println("Check your Download folder");
    }
    public void list(){
        System.out.println("Your files: ");
        File dir = new File("./UserFiles/"+user.getUsername());
        File[] files = dir.listFiles();
        if (files!=null){
            for(File file : files){
                System.out.println(file.getName());
            }
        }
        System.out.println(" ");
    }
    public void logOut(){
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("END");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String nameOfFile(String path){
        int k;
        String name = "";
        if(path.charAt(path.length()-1)=='/'){
            k=1;
        }
        else{
            k=0;
        }
        for (int i=path.length()-1-k;i>=0;i--){
            if(path.charAt(i)=='/'){
                return name;
            }
            else{
                name=path.charAt(i)+name;
            }
        }
        return name;
    }
}

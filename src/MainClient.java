import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClient {
    public static User loginUser = null;
    public static ArrayList<User> users = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        File members = new File("members");
        Scanner sc = new Scanner(members);
        String names;
        String passwords;
        while (sc.hasNextLine()){
            names=sc.nextLine();
            passwords=sc.nextLine();
            User user = new User(names,passwords);
            users.add(user);
        }
        Scanner in = new Scanner(System.in);
        int todo = -1;
        boolean flag = false;
        Client client= new Client();

        while (todo != 0) {
            if (!flag)
                System.out.println("1-login/register");
            if (flag)
                System.out.println("2-upload");
            if (flag)
                System.out.println("3-download");
            if (flag)
                System.out.println("4-list");
            if (flag)
                System.out.println("5-logOut");
            System.out.println("0-exit");
            String todoo = in.nextLine();
            try {
                todo = Integer.valueOf(todoo);
            }
            catch (Exception e){
                System.out.println("Ivalid Command");
            }
            switch(todo){
                case 0:
                    client.logOut();
                    break;
                case 1:
                    while(true) {
                        System.out.println("Please enter your username");
                        String username = in.nextLine();
                        System.out.println("Please enter your password");
                        String password = in.nextLine();
                        if(loginOrRegister(username,password)) {
                            File dir = new File("../"+username);
                            dir.mkdir();
                            break;
                        }
                    }
                    flag = true;
                    client.clientStart(loginUser);
                    break;
                case 2:
                    System.out.println("Please enter path");
                    String path=in.nextLine();
                    client.upload(path);
                    break;
                case 3:
                    System.out.println("Please enter file name");
                    String fileName=in.nextLine();
                    client.download(fileName);
                    break;
                case 4:
                    client.list();
                    break;
                case 5:
                    client.logOut();
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }

    }
    public static boolean loginOrRegister(String username , String password){
        for (User user: users) {
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    loginUser = user;
                    System.out.println("you logged in");
                    return true;
                }
                else{
                    System.out.println("Wrong Password");
                    return false;
                }
            }
        }
        if(checkPassword(password)) {
            User newUser = new User(username, password);
            users.add(newUser);
            loginUser=newUser;
            try
            {
                FileWriter fw = new FileWriter("members",true); //the true will append the new data
                fw.write(username+"\n");//appends the string to the file
                fw.write(password+"\n");//appends the string to the file
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
            boolean file = new File(username).mkdir();
            if(file)
                System.out.println("your Register complete");
            return true;
        }
        else{
            System.out.println("Weak password");
            return false;
        }
    }
    public static boolean checkPassword(String password){
        if(password.length()<6) return false;
        for(int i=0;i<password.length();i++){
            if(Character.isDigit(password.charAt(i))){
                return true;
            }
        }
        return false;
    }
}

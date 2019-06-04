package Client;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class User {
    private String username;
    private String password;
    public ArrayList<String> files = new ArrayList<>();
    User(String username,String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }

}

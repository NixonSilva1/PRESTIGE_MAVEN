package domain;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final String email;
    private final String password;
    
    public User(String name, String email, String password) {
        this.name = name; 
        this.email = email; 
        this.password = password;
    }
    
    public boolean login(String e, String p) { 
        return email.equals(e) && password.equals(p); 
    }
    
    public String getEmail() { return email; }
    public String getName() { return name; }
}
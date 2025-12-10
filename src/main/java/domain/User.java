package domain;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final String email;
    private final String password;
    private final String userType; // "CLIENT" o "ADMIN"

    public User(String n, String e, String p) {
        this(n, e, p, "CLIENT"); // Por defecto es cliente
    }
    
    public User(String n, String e, String p, String type) {
        name = n; 
        email = e; 
        password = p;
        userType = type;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
    
    public boolean isAdmin() {
        return "ADMIN".equals(userType);
    }
}
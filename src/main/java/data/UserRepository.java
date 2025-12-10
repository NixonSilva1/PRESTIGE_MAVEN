package data;

import domain.User;
import java.util.*;

public class UserRepository {
    private final FileRepository<User> repo = new FileRepository<>("users.dat");

    public void add(User user) throws Exception {
        List<User> all = repo.loadAll();
        all.add(user);
        repo.saveAll(all);
    }

    public List<User> getAll() throws Exception {
        return repo.loadAll();
    }
    
    public User findByEmail(String email) throws Exception {
        List<User> users = repo.loadAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean emailExists(String email) throws Exception {
        return findByEmail(email) != null;
    }
}
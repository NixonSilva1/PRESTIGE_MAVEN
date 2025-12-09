package data;

import java.io.*;
import java.util.*;

public class FileRepository<T> {
    private final String filename;
    
    public FileRepository(String f) { 
        filename = f; 
    }

    public void saveAll(List<T> items) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(items);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> loadAll() throws Exception {
        File f = new File(filename);
        if(!f.exists()) return new ArrayList<>();
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) in.readObject();
        }
    }
}
package domain;

import java.io.Serializable;
import java.util.HashSet;

public class Venue implements Serializable, IBookable {
    private static final long serialVersionUID = 1L;
    
    private final String name; 
    private final int capacity; 
    private final HashSet<String> reservedDates = new HashSet<>();

    public Venue(String n, int c) { 
        name = n; 
        capacity = c; 
    }

    @Override
    public boolean checkAvailability(String d) { 
        return !reservedDates.contains(d); 
    }
    
    @Override
    public void book(String d) { 
        reservedDates.add(d); 
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
}
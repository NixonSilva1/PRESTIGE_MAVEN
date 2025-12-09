package domain;

import java.io.Serializable;

public abstract class AbstractService implements Serializable, IPriced {
    private static final long serialVersionUID = 1L;
    
    protected final String name;
    protected final double price;
    
    public AbstractService(String n, double p) { 
        name = n; 
        price = p; 
    }
    
    @Override
    public double calculatePrice() { 
        return price; 
    }
    
    public abstract void addService(String date, String venue);
    
    public String getName() { 
        return name; 
    }
}
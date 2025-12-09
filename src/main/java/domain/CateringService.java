package domain;

public class CateringService extends AbstractService {
    private String menuDetails = "";
    
    public CateringService(double price) { 
        super("Catering", price); 
    }
    
    public CateringService(double price, String details) {
        super("Catering", price);
        this.menuDetails = details;
    }
    
    @Override
    public void addService(String date, String venue) {
        System.out.println("Catering booked for " + date + " at " + venue);
        if (!menuDetails.isEmpty()) {
            System.out.println("Menu Details: " + menuDetails);
        }
    }
    
    public String getMenuDetails() {
        return menuDetails;
    }
}
package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final User user;
    private final LocalDate date;
    private final Venue venue;
    private final ArrayList<AbstractService> services = new ArrayList<>();
    private double totalPrice;

    public Reservation(User u, LocalDate d, Venue v){
        user = u; 
        date = d; 
        venue = v;
        // IMPORTANTE: Inicializar totalPrice con el precio base del venue
        totalPrice = getVenueBasePrice(v.getName());
    }

    public void addService(AbstractService s){
        services.add(s);
        totalPrice += s.calculatePrice();
    }
    
    // Método auxiliar para obtener el precio base del venue
    private double getVenueBasePrice(String venueName) {
        return switch (venueName) {
            case "Hospice Club" -> 5000.0;
            case "house of mystery" -> 3500.0;
            case "Executive Hall" -> 2500.0;
            case "Ocean View Terrace" -> 4000.0;
            case "Intimate Lounge" -> 1800.0;
            default -> 0.0;
        };
    }

    public LocalDate getDate() { return date; }
    public User getUser() { return user; }
    public Venue getVenue() { return venue; }
    public double getTotalPrice() { return totalPrice; }
    public List<AbstractService> getServices() { return new ArrayList<>(services); }
}
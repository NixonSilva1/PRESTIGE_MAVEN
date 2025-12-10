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
    private String paymentStatus; // "PENDING" o "PAID"

    public Reservation(User u, LocalDate d, Venue v){
        user = u; 
        date = d; 
        venue = v;
        totalPrice = getVenueBasePrice(v.getName());
        paymentStatus = "PENDING"; // Por defecto pendiente
    }

    public void addService(AbstractService s){
        services.add(s);
        totalPrice += s.calculatePrice();
    }
    
    private double getVenueBasePrice(String venueName) {
        return switch (venueName) {
            case "Hospice Club", "Grand Ballroom" -> 5000.0;
            case "house of mystery", "Romantic Garden" -> 3500.0;
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
    public String getPaymentStatus() { return paymentStatus; }
    
    public void setPaymentStatus(String status) {
        this.paymentStatus = status;
    }
    
    public boolean isPaid() {
        return "PAID".equals(paymentStatus);
    }
}
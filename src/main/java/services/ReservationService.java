package services;

import data.ReservationRepository;
import domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repo) {
        repository = repo;
    }

    public void createReservation(User user, LocalDate date, Venue venue, List<AbstractService> services)
            throws Exception {
        Reservation reservation = new Reservation(user, date, venue);
        for (AbstractService service : services) {
            reservation.addService(service);
        }
        repository.add(reservation);
    }

    public List<Reservation> getAllReservations() throws Exception {
        return repository.getAll();
    }
    
    public List<Reservation> getReservationsByUser(String userEmail) throws Exception {
        List<Reservation> allReservations = repository.getAll();
        return allReservations.stream()
            .filter(r -> r.getUser().getEmail().equals(userEmail))
            .collect(Collectors.toList());
    }
    
    // Método para verificar si un lugar está disponible en una fecha
    public boolean isVenueAvailable(String venueName, LocalDate date) throws Exception {
        List<Reservation> allReservations = repository.getAll();
        for (Reservation reservation : allReservations) {
            if (reservation.getVenue().getName().equals(venueName) && 
                reservation.getDate().equals(date)) {
                return false;
            }
        }
        return true;
    }
    
    // Método para actualizar el estado de pago de una reservación
    public void updatePaymentStatus(Reservation reservation, String status) throws Exception {
        List<Reservation> allReservations = repository.getAll();
        for (int i = 0; i < allReservations.size(); i++) {
            Reservation r = allReservations.get(i);
            if (r.getUser().getEmail().equals(reservation.getUser().getEmail()) &&
                r.getDate().equals(reservation.getDate()) &&
                r.getVenue().getName().equals(reservation.getVenue().getName())) {
                r.setPaymentStatus(status);
                break;
            }
        }
        repository.updateAll(allReservations);
    }
}
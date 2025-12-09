package services;

import data.ReservationRepository;
import domain.*;

import java.time.LocalDate;
import java.util.List;

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
    
    // Método para verificar si un lugar está disponible en una fecha
    public boolean isVenueAvailable(String venueName, LocalDate date) throws Exception {
        List<Reservation> allReservations = repository.getAll();
        for (Reservation reservation : allReservations) {
            if (reservation.getVenue().getName().equals(venueName) && 
                reservation.getDate().equals(date)) {
                return false; // Ya existe una reserva
            }
        }
        return true; // Disponible
    }
}
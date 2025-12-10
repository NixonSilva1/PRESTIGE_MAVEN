package data;

import domain.Reservation;
import java.util.*;

public class ReservationRepository {
    private final FileRepository<Reservation> repo = new FileRepository<>("reservations.dat");

    public void add(Reservation r) throws Exception {
        List<Reservation> all = repo.loadAll();
        all.add(r);
        repo.saveAll(all);
    }

    public List<Reservation> getAll() throws Exception {
        return repo.loadAll();
    }
    
    public void updateAll(List<Reservation> reservations) throws Exception {
        repo.saveAll(reservations);
    }
}
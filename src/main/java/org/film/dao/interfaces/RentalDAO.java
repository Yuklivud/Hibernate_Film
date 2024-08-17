package org.film.dao.interfaces;

import org.film.entity.Rental;

import java.util.List;

public interface RentalDAO {
    void saveRental(Rental rental);
    Rental getRental(int id);
    List<Rental> getAllRentals();
    void updateRental(Rental rental);
    void deleteRental(int id);
}

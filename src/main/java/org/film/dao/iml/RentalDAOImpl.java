package org.film.dao.iml;


import org.film.entity.Customer;
import org.film.entity.Film;
import org.film.entity.Inventory;
import org.film.entity.Rental;
import org.film.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.Instant;

public class RentalDAOImpl {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void returnMovie(int customerId, int filmId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();

            Query<Rental> rentalQuery = session.createQuery(
                    "FROM Rental r WHERE r.customer.id = :customerId AND r.inventory.film.id = :filmId AND r.returnDate IS NULL",
                    Rental.class
            );

            rentalQuery.setParameter("customerId", customerId);
            rentalQuery.setParameter("filmId", filmId);

            Rental rental = rentalQuery.uniqueResult();

            if (rental == null) {
                throw new Exception("Rental not found for customerId: " + customerId + " and filmId: " + filmId);
            }
            rental.setReturnDate(Instant.now());
            session.update(rental);

            tx.commit();
        } catch (Throwable e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

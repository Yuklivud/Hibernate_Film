package org.film.dao.iml;


import org.film.dao.interfaces.RentalDAO;
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
import java.util.List;

public class RentalDAOImpl implements RentalDAO {
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

    @Override
    public void saveRental(Rental rental) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(rental);
            tx.commit();
        } catch (Throwable e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Rental getRental(int id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Rental.class, id);
        }
    }

    @Override
    public List<Rental> getAllRentals() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Rental").list();
        }
    }

    @Override
    public void updateRental(Rental rental) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(rental);
            tx.commit();
        } catch (Throwable e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRental(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(getRental(id));
            tx.commit();
        } catch (Throwable e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

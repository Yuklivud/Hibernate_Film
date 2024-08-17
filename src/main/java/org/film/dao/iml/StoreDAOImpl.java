package org.film.dao.iml;


import org.film.dao.interfaces.StoreDAO;
import org.film.entity.*;
import org.film.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class StoreDAOImpl implements StoreDAO {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void saveStoreById(Store store) {

    }

    public void customerRentalInventory(Customer customer, Store store, int filmId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query<Long> queryCount = session.createQuery("select count(*) from Inventory where film.id=:filmId and store.id=:storeId", Long.class);
            queryCount.setParameter("filmId", filmId);
            queryCount.setParameter("storeId", store.getId());
            if(queryCount.uniqueResult() <= 0) throw new Exception("This movie is not available at this store.");


            Query<Rental> queryRental = session.createQuery(
                    "select r from Rental r where r.inventory.film.id=:filmId and r.inventory.store.id=:storeId and r.returnDate is null", Rental.class);
            queryRental.setParameter("filmId", filmId);
            queryRental.setParameter("storeId", store.getId());

            Rental currentRental = queryRental.uniqueResult();

            if (currentRental != null) throw new Exception("This film is unavailable right now.");

            Query<Inventory> queryInventory = session.createQuery(
                    "from Inventory where film.id=:filmId and store.id=:storeId", Inventory.class);
            queryInventory.setParameter("filmId", filmId);
            queryInventory.setParameter("storeId", store.getId());

            Inventory availableInventory = queryInventory.setMaxResults(1).uniqueResult();
            if (availableInventory == null) {
                throw new Exception("Inventory is not available at this store.");
            }

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(availableInventory);
            rental.setStaff(store.getManagerStaff());
            rental.setReturnDate(Instant.now());
            session.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(store.getManagerStaff());
            payment.setRental(rental);
            payment.setAmount(BigDecimal.valueOf(9.99));
            payment.setPaymentDate(Instant.now());
            session.save(payment);

            tx.commit();
        } catch (Throwable e){
            e.printStackTrace();
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Throwable rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        }
    }

    @Override
    public void updateStoreById(Store store) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(store);
            tx.commit();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStoreById(Store store) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(store);
            tx.commit();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public Store getStoreById(int id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Store.class, id);
        }
    }

    @Override
    public List<Store> getAllStores() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Store").list();
        }
    }
}

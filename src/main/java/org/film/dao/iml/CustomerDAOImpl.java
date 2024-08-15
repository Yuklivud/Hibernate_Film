package org.film.dao.iml;

import org.film.dao.interfaces.CustomerDAO;
import org.film.entity.Address;
import org.film.entity.Customer;
import org.film.entity.Store;
import org.film.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void saveCustomer(Customer customer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(customer);
            tx.commit();
        } catch (Throwable e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    public void saveWithDependencyCustomer(Customer customer, Store store, Address address) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.getTransaction();
            tx.begin();

            session.saveOrUpdate(store);
            session.saveOrUpdate(address);

            customer.setStore(store);
            customer.setAddress(address);

            session.save(customer);
            tx.commit();
        } catch (Throwable e) {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Customer.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Customer> getALlCustomers() {
        Session session = sessionFactory.openSession();
        try {
            Query<Customer> query = session.createQuery("from Customer", Customer.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.getTransaction();
            tx.begin();
            session.update(customer);
            tx.commit();
        } catch (Throwable e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.getTransaction();
            tx.begin();
            session.delete(customer);
            tx.commit();
        } catch (Throwable e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}

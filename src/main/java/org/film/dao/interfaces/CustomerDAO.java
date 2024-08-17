package org.film.dao.interfaces;

import org.film.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    void saveCustomer(Customer customer);
    Customer getCustomerById(int id);
    List<Customer> getALlCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}

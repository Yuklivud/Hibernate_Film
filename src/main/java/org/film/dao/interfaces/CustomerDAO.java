package org.film.dao.interfaces;

import org.film.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    public void saveCustomer(Customer customer);
    public Customer getCustomerById(int id);
    public List<Customer> getALlCustomers();
    public void updateCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
}

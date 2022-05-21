package com.webcustomer_tracker.dao;

import java.util.List;
import com.webcustomer_tracker.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void save(Customer customer);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomers(String theSearchName);

}

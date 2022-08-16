package com.webcustomer_tracker.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcustomer_tracker.dao.CustomerDAO;
import com.webcustomer_tracker.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	// need to inject customer dao
	@Autowired
	private CustomerDAO customerDAO;

	/* OLD METHOD TAKE A LOOK IN CONTROLLER DESCRIPTION
	 * @Override
	 *
	 * @Transactional public List<Customer> getCustomers() { return
	 * customerDAO.getCustomers(); }
	 */

	@Override
	@Transactional
	public List<Customer> getCustomers(int theSortField) {
		return customerDAO.getCustomers(theSortField);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		customerDAO.save(customer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) {
		customerDAO.deleteCustomer(id);
	}

	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName) {
		return customerDAO.searchCustomers(theSearchName);
	}



}

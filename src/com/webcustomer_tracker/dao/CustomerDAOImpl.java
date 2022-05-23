package com.webcustomer_tracker.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webcustomer_tracker.entity.Customer;
import com.webcustomer_tracker.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory; // sessionFactory koji je mapiran u spring-mvc-crud-demo-servlet.xml fajlu
											// bean id="sessionFactory"

	/*
	 * @Override public List<Customer> getCustomers() {
	 * 
	 * // get the current hibernate session Session currentSession =
	 * sessionFactory.getCurrentSession();
	 * 
	 * // create a query... sort by last name Query<Customer> theQuery =
	 * currentSession.createQuery("from Customer order by lastName ",
	 * Customer.class); // pay attention on lastName property, it is object Customer
	 * property defined, not db column
	 * 
	 * // execute query and get result List<Customer> customers =
	 * theQuery.getResultList();
	 * 
	 * // return the results return customers; }
	 */
	
	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// determine sort field
		String theFieldName = null;
		
		switch (theSortField) {
			case SortUtils.FIRST_NAME: 
				theFieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				// if nothing matches the default to sort by lastName
				theFieldName = "lastName";
		}
		
		// create a query  
		String queryString = "from Customer order by " + theFieldName;
		Query<Customer> theQuery = 
				currentSession.createQuery(queryString, Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void save(Customer customer) {

		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// save the customer...
//		session.save(customer);

		// save or update custoemr depending on weather he has id or not
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {

		// get current Hibernate session
		Session session = sessionFactory.getCurrentSession();

		// retrieve/read customer from database using id
		Customer customer = session.get(Customer.class, id);

		return customer;
	}

	@Override
	public void deleteCustomer(int id) {

		// get current Hibernate session
		Session session = sessionFactory.getCurrentSession();

		// delete customer with primary key (or id)
		Query query = session.createQuery("delete from Customer where id=:customerId");

		query.setParameter("customerId", id);

		// executeUpdate is generic method for whatever SQL you have to execute it will
		// process it, so it is same gfor update/delete...
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = null;

		//
		// only search by name if theSearchName is not empty
		//
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			
			// search for firstName or lastName ... case insensitive
			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;

	}

}

package com.webcustomer_tracker.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webcustomer_tracker.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory; // sessionFactory koji je mapiran u spring-mvc-crud-demo-servlet.xml fajlu
											// bean id="sessionFactory"

	@Override
	public List<Customer> getCustomers() {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query... sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName ", 
				Customer.class); // pay attention on lastName property, it is object Customer property defined, not db column

		// execute query and get result
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;
	}

	@Override
	public void save(Customer customer) {

		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// save the customer...
		session.save(customer);
	}

}

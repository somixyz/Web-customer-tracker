package com.webcustomer_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webcustomer_tracker.entity.Customer;
import com.webcustomer_tracker.service.CustomerService;
import com.webcustomer_tracker.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer service
	@Autowired
	private CustomerService customerService;

	/**  OLD METHOD, BEFORE ADDING SORT COLUMN OPTION
	@GetMapping("/list")
	public String listCustomer(Model model) {

		// get customers from the DAO
		List<Customer> theCustomers = customerService.getCustomers();

		// add the customers to the model
		model.addAttribute("customers", theCustomers);

		return "list-customers";
	}
	*/


	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required=false) String sort) {

		// get customers from the service
		List<Customer> theCustomers = null;

		// check for sort field
		if (sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		}
		else {
			// no sort field provided ... default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}

		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		// create model attribute to bind form data
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {

		// save the customer using our service
		customerService.saveCustomer(customer);
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {

		// get the customer from service
		Customer customer = customerService.getCustomer(id);

		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);

		// send over to out form
		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {

		// delete the customer
		customerService.deleteCustomer(id);

		return "redirect:/customer/list";
	}

	  @GetMapping("/search")
	    public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {

	        // search customers from the service
	        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

	        // add the customers to the model
	        theModel.addAttribute("customers", theCustomers);
	        return "list-customers";
	    }


}

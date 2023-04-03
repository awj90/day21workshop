package sg.edu.nus.iss.day21workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day21workshop.model.Customer;
import sg.edu.nus.iss.day21workshop.model.Order;
import sg.edu.nus.iss.day21workshop.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(Integer limit, Integer offset) {
        return customerRepository.getAllCustomers(limit, offset);
    }

    public Customer findCustomerById(Integer id) {
        return customerRepository.findCustomerById(id);
    }

    public List<Order> getCustomerOrders(Integer id) {
        return customerRepository.getCustomerOrders(id);
    }
}

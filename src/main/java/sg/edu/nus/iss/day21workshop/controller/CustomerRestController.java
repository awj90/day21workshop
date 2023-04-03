package sg.edu.nus.iss.day21workshop.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.day21workshop.model.Customer;
import sg.edu.nus.iss.day21workshop.model.Order;
import sg.edu.nus.iss.day21workshop.service.CustomerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {
    
    @Autowired
    CustomerService customerService;

    // Examples: http://localhost:8080/api/customers, http://localhost:8080/api/customers?limit=3&offset=2
    @GetMapping(path="/customers")
    public ResponseEntity<String> getAllCustomers(@RequestParam(required=false, defaultValue = "5") String limit, @RequestParam(required=false, defaultValue = "0") String offset) {

        List<Customer> customers = customerService.getAllCustomers(Integer.parseInt(limit), Integer.parseInt(offset));

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Customer c : customers) {
            arrayBuilder.add(c.toJsonObject());
        }

        JsonArray result = arrayBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(result.toString());
    }

    @GetMapping(path="/customer/{id}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer id) {

        JsonObject result = null;
        try {
            Customer customer = customerService.findCustomerById(id);
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("customer", customer.toJsonObject());
            result = jsonObjectBuilder.build();
        } catch(IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Json.createObjectBuilder().add("Error Message", "Record Not Found").build().toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(result.toString());
    }

    @GetMapping(path="/customer/{id}/orders")
    public ResponseEntity<String> getOrdersForCustomer(@PathVariable Integer id) {
        List<Order> orders = new ArrayList<>();
        JsonArray result = null;
        orders = customerService.getCustomerOrders(id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        
        for (Order order: orders) {
            jsonArrayBuilder.add(order.toJson());
        }
        result = jsonArrayBuilder.build();
     
        if (result.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("Error Message", "Records Not Found").build().toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(result.toString());
    }
}

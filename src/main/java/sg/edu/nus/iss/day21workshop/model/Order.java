package sg.edu.nus.iss.day21workshop.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    private Integer id;
    private String company;
    private String shipName;
    private Double shippingFee;
    private Customer customer;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public Double getShippingFee() {
        return shippingFee;
    }
    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", company=" + company + ", shipName=" + shipName + ", shippingFee=" + shippingFee
                + ", customer=" + customer + "]";
    }

    public static Order create(SqlRowSet rs) {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customer_id"));        
        
        Order order = new Order();
        order.setId(rs.getInt("order_id"));
        order.setShipName(rs.getString("ship_name"));
        order.setShippingFee(rs.getDouble("shipping_fee"));
        order.setCustomer(customer);
        return order;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder().add("customer_id", this.getCustomer().getId())
                                        .add("order_id", this.getId())
                                        .add("ship_name", this.getShipName())
                                        .add("shipping_fee", this.getShippingFee())
                                        .build();
    }
}

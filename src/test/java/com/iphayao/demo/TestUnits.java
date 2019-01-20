package com.iphayao.demo;

import com.iphayao.demo.customer.Customer;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class TestUnits {
    public static List<Customer> mockCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            customers.add(mockCustomer());
        }
        return customers;
    }

    public static Mono<Customer> mockCustomerMono() {
        return Mono.just(mockCustomer());
    }

    public static Mono<Customer> mockCustomerForUpdate() {
        Customer customer = mockCustomer();
        customer.setAge(21);
        return Mono.just(customer);
    }

    public static Customer mockCustomer() {
        Customer c = new Customer();
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setAge(20);
        c.setEmail("john.doe@mail.com");
        return c;
    }
}

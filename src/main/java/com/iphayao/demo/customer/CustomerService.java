package com.iphayao.demo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Flux<Customer> getCustomers() {
        return repository.findAll();
    }

    public Mono<Customer> getCustomerById(String id) {
        return repository.findById(id);
    }

    public Mono<Customer> getCustomerByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public Mono<Customer> createCustomer(Mono<Customer> customer) {
        return customer.flatMap(c -> repository.save(c));
    }

    public Mono<Customer> updateCustomerById(String id, Mono<Customer> customer) {
        return customer.flatMap(c -> repository.findById(id).flatMap(s -> {
            c.setId(s.getId());
            return repository.save(c);
        }));
    }

    public Mono<Void> deleteCustomer(String id) {
        return repository.deleteById(id);
    }
}

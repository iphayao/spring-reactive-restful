package com.iphayao.demo.customer;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
    Mono<Customer> findByFirstName(String fistName);
}

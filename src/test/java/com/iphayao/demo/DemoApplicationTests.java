package com.iphayao.demo;

import com.iphayao.demo.customer.Customer;
import com.iphayao.demo.customer.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CustomerRepository repository;

    private WebTestClient client;

    @Before
    public void setUp() throws Exception {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void givenCustomers_whenGetMethod_thenReturnOkCustomersBody() {
        repository.saveAll(TestUnits.mockCustomers()).collectList().block();

        client.get().uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class)
                .hasSize(4);
    }

    @Test
    public void givenID_whenGetMethodByID_thenReturnOkCustomerBody() {
        String mockedId = repository.save(TestUnits.mockCustomer()).map(Customer::getId).block();

        client.get().uri(String.format("/customers/%s", mockedId))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class);
    }

    @Test
    public void givenFistName_whenGetMethodByFirstName_thenReturnOkCustomerBody() {
        String mockedId = repository.save(TestUnits.mockCustomer()).map(Customer::getFirstName).block();

        client.get().uri(String.format("/customers?name=%s", mockedId))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class);
    }

    @Test
    public void givenCustomer_whenPostMethod_thenReturnCreatedCustomerBody() {
        client.post().uri("/customers")
                .body(TestUnits.mockCustomerMono(), Customer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Customer.class);
    }

    @Test
    public void givenID_whenPutMethodByID_thenReturnOkCustomerBody() {
        String mockedId = repository.save(TestUnits.mockCustomer()).map(Customer::getId).block();

        client.put().uri(String.format("/customers/%s", mockedId))
                .body(TestUnits.mockCustomerForUpdate(), Customer.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.age").isEqualTo(21);
    }

    @Test
    public void givenID_whenDeleteMethodByID_thenReturnOkNoBody() {
        String mockedId = repository.save(TestUnits.mockCustomer()).map(Customer::getId).block();

        client.delete().uri(String.format("/customers/%s", mockedId))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .isEmpty();
    }
}


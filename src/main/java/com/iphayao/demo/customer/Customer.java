package com.iphayao.demo.customer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Document
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
}

package com.cars24.ai_loan_assistance.data.entities;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "user")
public class UserEntity {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("phoneNumber")
    private String phone;
    @Field("role")
    private String role;

}

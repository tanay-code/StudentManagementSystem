package com.cts.sms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @NotBlank(message = "Name is mandatory")
	    private String name;

	    @Email(message = "Email should be valid")
	    @NotBlank(message = "Email is mandatory")
	    private String email;
	    
	    private int userId;

}
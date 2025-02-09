package com.cts.sms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;

    private String courseName;

    private String description;

    private String duration; // Duration in weeks or months

    private List<Integer> studentsEnrolled; // Many-to-Many Relationship
}

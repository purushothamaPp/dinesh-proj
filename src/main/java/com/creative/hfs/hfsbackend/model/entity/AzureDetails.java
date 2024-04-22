package com.creative.hfs.hfsbackend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "azuredetails")
public class AzureDetails {

    @Id
    @Column(name = "employee_id") // Specify the correct column name in the database
    private Integer employeeId;

    @Column(name = "first_name") // Specify the correct column name in the database
    private String firstName;

    @Column(name = "last_name") // Specify the correct column name in the database
    private String lastName;
}

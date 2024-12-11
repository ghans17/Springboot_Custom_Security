package com.argusoft.authmodule.MenuManagement.enities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String status;

    // Getters and Setters
}
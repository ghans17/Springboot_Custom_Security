package com.argusoft.authmodule.MenuManagement.enities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private Long parent;
    @Column(name = "order_number") // Change the column name
    private Integer orderNumber;
    private String status;

    // Getters and Setters
}
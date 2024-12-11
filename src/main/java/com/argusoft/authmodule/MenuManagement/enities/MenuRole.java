package com.argusoft.authmodule.MenuManagement.enities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Menu_Role_Mapping")
public class MenuRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleAppId role;

    private String status;

    // Getters and Setters
}

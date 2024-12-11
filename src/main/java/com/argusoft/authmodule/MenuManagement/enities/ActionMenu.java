package com.argusoft.authmodule.MenuManagement.enities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Action_Menu_Mapping")
public class ActionMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;

    // Getters and Setters
}
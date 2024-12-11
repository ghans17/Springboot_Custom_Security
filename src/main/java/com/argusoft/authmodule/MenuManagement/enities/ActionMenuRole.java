package com.argusoft.authmodule.MenuManagement.enities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Action_Menu_Role_Mapping")
public class ActionMenuRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_role_mapping_id")
    private MenuRole menuRole;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;

    private String status;

    // Getters and Setters
}

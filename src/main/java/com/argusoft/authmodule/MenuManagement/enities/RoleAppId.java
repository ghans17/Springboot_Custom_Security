package com.argusoft.authmodule.MenuManagement.enities;


import com.argusoft.authmodule.entities.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_Role_AppId_Mapping")
public class RoleAppId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String appId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

   
}
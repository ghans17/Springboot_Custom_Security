package com.argusoft.authmodule.entities;

import com.argusoft.authmodule.MenuManagement.enities.RoleAppId;
import com.argusoft.authmodule.custom.EncryptionConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "TBL_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //encrypted before being stored in the database and decrypted when retrieved
    @Convert(converter = EncryptionConverter.class)
    private String firstName;

    @Convert(converter = EncryptionConverter.class)
    private String lastName;

    //unique and cannot be null
    @Convert(converter = EncryptionConverter.class)
    @Column(unique = true, nullable = false)
    private String email;

    @Convert(converter = EncryptionConverter.class)
    @Column(unique = true, nullable = false)
    private String username;

    @Column()
    private String password;

    private String phone;

    @OneToMany(mappedBy = "user")
    private Set<RoleAppId> roles;
}

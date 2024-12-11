package com.argusoft.authmodule.MenuManagement.Reposotories;


import com.argusoft.authmodule.MenuManagement.enities.RoleAppId;
import com.argusoft.authmodule.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAppIdRepository extends JpaRepository<RoleAppId, Long> {
    List<RoleAppId> findByUser(User user);
}
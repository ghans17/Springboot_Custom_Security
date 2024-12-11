package com.argusoft.authmodule.MenuManagement.Reposotories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argusoft.authmodule.MenuManagement.enities.ActionMenuRole;

@Repository
public interface ActionMenuRoleRepository extends JpaRepository<ActionMenuRole, Long> {

    // The first parameter is appId (String) and the second is menuId (Long)
    List<ActionMenuRole> findByMenuRole_Menu_IdAndMenuRole_Role_AppId(Long menuId, String appId);

}

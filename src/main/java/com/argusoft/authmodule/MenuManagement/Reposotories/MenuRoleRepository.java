package com.argusoft.authmodule.MenuManagement.Reposotories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.argusoft.authmodule.MenuManagement.enities.MenuRole;
@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

    @Query("SELECT mr FROM MenuRole mr WHERE mr.menu.id = :menuId AND mr.role.appId = :appId")
    List<MenuRole> findByMenuAndAppId(@Param("menuId") Long menuId, @Param("appId") String appId);
}

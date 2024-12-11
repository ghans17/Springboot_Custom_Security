package com.argusoft.authmodule.MenuManagement.Reposotories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argusoft.authmodule.MenuManagement.enities.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByUrl(String url);
}

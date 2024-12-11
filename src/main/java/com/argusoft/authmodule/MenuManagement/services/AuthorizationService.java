package com.argusoft.authmodule.MenuManagement.services;

import com.argusoft.authmodule.MenuManagement.Reposotories.ActionMenuRoleRepository;
import com.argusoft.authmodule.MenuManagement.Reposotories.MenuRepository;
import com.argusoft.authmodule.MenuManagement.Reposotories.MenuRoleRepository;
import com.argusoft.authmodule.MenuManagement.Reposotories.RoleAppIdRepository;
import com.argusoft.authmodule.MenuManagement.enities.Menu;
import com.argusoft.authmodule.MenuManagement.enities.RoleAppId;
import com.argusoft.authmodule.MenuManagement.exceptions.AccessDeniedException;
import com.argusoft.authmodule.MenuManagement.exceptions.ResourceNotFoundException;
import com.argusoft.authmodule.entities.User;
import com.argusoft.authmodule.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuRoleRepository menuRoleRepository;

    @Autowired
    private ActionMenuRoleRepository actionMenuRoleRepository;

    @Autowired
    private RoleAppIdRepository roleAppIdRepository;

    /**
     * Check if the user has access to the specified App-ID
     *
     * @param username the username of the user
     * @param appId    the application ID
     * @return true if the user has access, otherwise throws an exception
     */
    public boolean hasAccessToAppId(String username, String appId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found: " + username);
        }
        User user = userOptional.get();

        List<RoleAppId> userRoles = roleAppIdRepository.findByUser(user);
        boolean hasAccess = userRoles.stream()
                .anyMatch(roleAppId -> roleAppId.getAppId().equals(appId));

        if (!hasAccess) {
            throw new AccessDeniedException("Access denied to App-ID: " + appId);
        }
        return true;
    }

    /**
     * Check if the user has menu access
     *
     * @param username the username of the user
     * @param appId    the application ID
     * @param url      the menu URL
     * @return true if the user has menu access, otherwise false
     */
    public boolean hasMenuAccess(String username, String appId, String url) {
        Optional<Menu> menuOptional = menuRepository.findByUrl(url);
        if (menuOptional.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found for URL: " + url);
        }
        Menu menu = menuOptional.get();

        boolean hasAccess = menuRoleRepository.findByMenuAndAppId(menu.getId(), appId)
                .stream()
                .anyMatch(menuRole -> menuRole.getRole().getUser().getUsername().equals(username));

        if (!hasAccess) {
            throw new AccessDeniedException("Access denied to menu for URL: " + url);
        }
        return true;
    }

    /**
     * Check if the user has action access
     *
     * @param username the username of the user
     * @param appId    the application ID
     * @param url      the menu URL
     * @return true if the user has action access, otherwise false
     */
    public boolean hasActionAccess(String username, String appId, String url) {
        Optional<Menu> menuOptional = menuRepository.findByUrl(url);
        if (menuOptional.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found for URL: " + url);
        }
        Menu menu = menuOptional.get();

        boolean hasAccess = actionMenuRoleRepository
                .findByMenuRole_Menu_IdAndMenuRole_Role_AppId(menu.getId(), appId)
                .stream()
                .anyMatch(actionMenuRole -> actionMenuRole.getMenuRole().getRole().getUser().getUsername().equals(username));

        if (!hasAccess) {
            throw new AccessDeniedException("Access denied for actions on menu URL: " + url);
        }
        return true;
    }
}

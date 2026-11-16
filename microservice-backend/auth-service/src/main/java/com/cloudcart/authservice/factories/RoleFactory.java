package com.cloudcart.authservice.factories;

import com.cloudcart.authservice.enums.ERole;
import com.cloudcart.authservice.exceptions.RoleNotFoundException;
import com.cloudcart.authservice.modals.Role;
import com.cloudcart.authservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleFactory {
    private final RoleService roleService;

    public Role getInstance(String role) throws RoleNotFoundException {
        if (role.equals("admin")) {
            return roleService.findByName(ERole.ROLE_ADMIN);
        }
        else if (role.equals("user")){
            return roleService.findByName(ERole.ROLE_USER);
        }
        throw new RoleNotFoundException("Invalid role name: " + role);
    }

}

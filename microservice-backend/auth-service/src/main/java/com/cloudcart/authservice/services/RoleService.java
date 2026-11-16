package com.cloudcart.authservice.services;

import com.cloudcart.authservice.enums.ERole;
import com.cloudcart.authservice.modals.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(ERole eRole);
}
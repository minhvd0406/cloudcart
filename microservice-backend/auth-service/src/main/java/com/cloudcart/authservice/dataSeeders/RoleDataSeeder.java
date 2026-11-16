package com.cloudcart.authservice.dataSeeders;

import com.cloudcart.authservice.enums.ERole;
import com.cloudcart.authservice.modals.Role;
import com.cloudcart.authservice.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDataSeeder {
    private final RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void loadRoles(ContextRefreshedEvent event) {

        List<ERole> roles = Arrays.stream(ERole.values()).toList();
        for(ERole erole: roles) {
            if (!roleRepository.existsByName(erole)) {
                roleRepository.save(new Role(erole));
            }
        }
    }

}

package com.jfcbxp.auth.config;

import com.jfcbxp.auth.domain.Permission;
import com.jfcbxp.auth.domain.User;
import com.jfcbxp.auth.repository.PermissionRepository;
import com.jfcbxp.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public void startBD() {
        Permission permission = new Permission();
        permission.setDescription("Admin");
        permission = permissionRepository.save(permission);

        User admin = new User();
        admin.setUserName("jfcbxp");
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setPermissions(List.of(permission));

        userRepository.save(admin);


    }
}

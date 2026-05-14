package com.eventix.eventix;

import com.eventix.eventix.domain.EventEntity;
import com.eventix.eventix.domain.UserEntity;
import com.eventix.eventix.repo.EventRepository;
import com.eventix.eventix.repo.PermissionRepository;
import com.eventix.eventix.repo.RoleRepository;

import com.eventix.eventix.repo.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class LiquibaseIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("eventix")
            .withUsername("eventix")
            .withPassword("eventix");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Test
    void liquibaseShouldCreateTablesAndJpaShouldWork() {
        UserEntity user = new UserEntity();
        user.setEmail("alice@example.com");
        user.setFullName("Alice");
        user.setCreatedAt(OffsetDateTime.now());
        user = userRepository.save(user);

        // assign two global roles to prove user_roles join works
        var userRole = roleRepository.findByCode("USER").orElseThrow();
        var adminRole = roleRepository.findByCode("ADMIN").orElseThrow();
        user.setRoles(new java.util.HashSet<>(java.util.List.of(userRole, adminRole)));
        user = userRepository.save(user);

        // verify permissions are seeded
        var permission = permissionRepository.findByCode("ADMIN_USER_MANAGE").orElseThrow();
        assertThat(permission.getCode()).isEqualTo("ADMIN_USER_MANAGE");

        EventEntity event = new EventEntity();
        event.setOrganizer(user);
        event.setTitle("Enterprise Event");
        event.setStartsAt(OffsetDateTime.now().plusDays(1));
        event.setCreatedAt(OffsetDateTime.now());
        event = eventRepository.save(event);

        assertThat(userRepository.findById(user.getId())).isPresent();
        assertThat(eventRepository.findById(event.getId())).isPresent();
    }
}



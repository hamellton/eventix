package com.eventix.eventix.repo;

import com.eventix.eventix.domain.RolePermissionEntity;
import com.eventix.eventix.domain.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {
}



package com.telefonica.mshispamjobprivate.users.repository;

import com.telefonica.mshispamjobprivate.users.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

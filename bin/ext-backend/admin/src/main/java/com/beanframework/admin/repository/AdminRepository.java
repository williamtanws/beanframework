package com.beanframework.admin.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.admin.domain.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID>, JpaSpecificationExecutor<Admin> {

	Optional<Admin> findByUuid(UUID uuid);

	Optional<Admin> findById(String id);
}

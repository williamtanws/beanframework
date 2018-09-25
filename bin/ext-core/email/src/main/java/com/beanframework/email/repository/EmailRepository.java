package com.beanframework.email.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.email.domain.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID>, JpaSpecificationExecutor<Email> {

	Optional<Email> findByUuid(UUID uuid);
}

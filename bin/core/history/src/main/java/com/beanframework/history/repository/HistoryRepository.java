package com.beanframework.history.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.history.domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID>, JpaSpecificationExecutor<History> {

	Optional<History> findByUuid(UUID uuid);
}

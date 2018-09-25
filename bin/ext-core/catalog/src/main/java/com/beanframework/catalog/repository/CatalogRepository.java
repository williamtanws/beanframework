package com.beanframework.catalog.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beanframework.catalog.domain.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, UUID>, JpaSpecificationExecutor<Catalog> {

	Optional<Catalog> findByUuid(UUID uuid);

	Optional<Catalog> findById(String id);

	@Query("select count(a) > 0 from " + Catalog.MODEL + " a where a." + Catalog.ID + " = :id")
	boolean isIdExists(String id);

}

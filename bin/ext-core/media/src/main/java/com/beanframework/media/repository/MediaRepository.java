package com.beanframework.media.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beanframework.media.domain.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID>, JpaSpecificationExecutor<Media> {

	Optional<Media> findByUuid(UUID uuid);

	Optional<Media> findById(String id);

	@Query("select count(a) > 0 from " + Media.MODEL + " a where a." + Media.ID + " = :id")
	boolean isIdExists(String id);

}

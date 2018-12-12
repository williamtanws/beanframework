package com.beanframework.language.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beanframework.language.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID>, JpaSpecificationExecutor<Language> {

	Optional<Language> findByUuid(UUID uuid);

	Optional<Language> findById(String id);

	List<Language> findByActiveTrueOrderBySortAsc();

	List<Language> findByOrderBySortAsc();

	@Query("select count(a) > 0 from " + Language.DOMAIN + " a where a." + Language.ID + " = :id")
	boolean isIdExists(String id);

}

package com.beanframework.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beanframework.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	Optional<User> findByUuid(UUID uuid);

	Optional<User> findById(String id);

	@Query("select count(a) > 0 from " + User.MODEL + " a where a." + User.ID + " = :id")
	boolean isIdExists(@Param("id") String id);
}

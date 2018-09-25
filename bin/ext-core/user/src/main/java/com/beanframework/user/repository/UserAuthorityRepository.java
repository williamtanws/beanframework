package com.beanframework.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beanframework.user.domain.UserAuthority;

@Repository
public interface UserAuthorityRepository
		extends JpaRepository<UserAuthority, UUID>, JpaSpecificationExecutor<UserAuthority> {

	@Query("SELECT u FROM UserAuthority u WHERE u.userGroup.uuid = :uuid")
	List<UserAuthority> findByUserGroupUuid(@Param("uuid") UUID uuid);

	@Query("SELECT u FROM UserAuthority u WHERE u.userGroup.uuid = :userGroupUuid and u.userPermission.uuid = :userPermissionUuid and u.userRight.uuid = :userRightUuid")
	Optional<UserAuthority> findByUserGroupUuidAndUserPermissionUuidAndUserRightUuid(
			@Param("userGroupUuid") UUID userGroupUuid, @Param("userPermissionUuid") UUID userPermissionUuid,
			@Param("userRightUuid") UUID userRightUuid);

	@Modifying
	@Query("DELETE FROM UserAuthority u WHERE u.userRight.uuid = :uuid")
	void deleteByUserRightUuid(@Param("uuid") UUID uuid);

	@Modifying
	@Query("DELETE FROM UserAuthority u WHERE u.userPermission.uuid = :uuid")
	void deleteByUserPermissionUuid(@Param("uuid") UUID uuid);
}

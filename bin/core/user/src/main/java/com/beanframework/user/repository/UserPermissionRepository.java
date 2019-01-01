//package com.beanframework.user.repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.beanframework.user.domain.UserGroup;
//import com.beanframework.user.domain.UserPermission;
//
//@Repository
//public interface UserPermissionRepository
//		extends JpaRepository<UserPermission, UUID>, JpaSpecificationExecutor<UserPermission> {
//
//	Optional<UserPermission> findByUuid(UUID uuid);
//
//	Optional<UserPermission> findById(String id);
//
//	@Query("select count(a) > 0 from " + UserGroup.DOMAIN + " a where a." + UserGroup.ID + " = :id")
//	boolean isIdExists(@Param("id") String id);
//
//	List<UserPermission> findByOrderBySortAsc();
//
//}

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
//import com.beanframework.user.domain.UserRight;
//
//@Repository
//public interface UserRightRepository extends JpaRepository<UserRight, UUID>, JpaSpecificationExecutor<UserRight> {
//
//	Optional<UserRight> findByUuid(UUID uuid);
//
//	Optional<UserRight> findById(String id);
//	
//	@Query("select count(a) > 0 from " + UserRight.DOMAIN + " a where a." + UserRight.ID + " = :id")
//	boolean isIdExists(@Param("id") String id);
//
//	List<UserRight> findByOrderBySortAsc();
//}

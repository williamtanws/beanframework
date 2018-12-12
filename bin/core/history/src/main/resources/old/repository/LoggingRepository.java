package com.beanframework.logging.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beanframework.logging.domain.Logging;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, UUID>, JpaSpecificationExecutor<Logging> {
	
	@Query("select l.uuid from "+Logging.DOMAIN+" l")
	List<UUID> getLastestLogId(Pageable pageable);

	@Modifying
    @Query("delete from "+Logging.DOMAIN+" l where l.uuid not in (:uuidList)")
	int deleteOldLogByExcludedId(@Param("uuidList") List<UUID> idList);

	@Modifying
	@Query("delete from "+Logging.DOMAIN+" l WHERE l.createdDate < :date")
	int removeOlderThan(@Param("date") java.sql.Date date);
	
}
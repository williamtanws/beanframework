package com.beanframework.menu.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beanframework.menu.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor<Menu> {

	Optional<Menu> findByUuid(UUID uuid);

	Optional<Menu> findById(String id);

	Optional<Menu> findByPath(String path);

	@Query("select m from Menu m where m.parent is null and m.enabled = 1 order by m.sort asc")
	List<Menu> findNavigationTree();
	
	@Query("select m from Menu m where m.parent = null order by m.sort asc")
	List<Menu> findByParentNullOrderBySort();

	@Query("select m from Menu m where m.parent.uuid = :uuid order by m.sort asc")
	List<Menu> findByParentUuidOrderBySort(@Param("uuid") UUID toUuid);
	
	@Modifying
	@Query("update Menu m set m.parent.uuid = :parentUuid where m.uuid = :uuid")
	void updateParentByUuid(@Param("uuid") UUID uuid, @Param("parentUuid") UUID parentUuid);

	@Modifying
	@Query("update Menu m set m.sort = :sort where m.uuid = :uuid")
	void updateSortByUuid(@Param("uuid") UUID uuid, @Param("sort") int sort);

	@Modifying
	@Query("update Menu m set m.parent = null where m.uuid = :uuid")
	void setParentNullByUuid(@Param("uuid") UUID fromUuid);


}

package com.beanframework.menu.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.menu.domain.Menu;

@Repository
public interface MenuRepository<T extends Menu> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

}

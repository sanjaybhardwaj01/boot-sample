package com.myapp.repository;

import com.myapp.domain.Continent;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Continent entity.
 */
@SuppressWarnings("unused")
public interface ContinentRepository extends JpaRepository<Continent,Long> {
	
	//@Procedure(name = "getContinents")
	//List<String> getContinentByName(@Param("inContinentName") String inContinentName);
	//List<String> getContinentByName();
	
	@Query(value = "SELECT continent_name from continent where continent_name = ?1" , nativeQuery = true)
	List<Object> getContinentByName(String inContinentName);	
}

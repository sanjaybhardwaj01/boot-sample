package com.myapp.service;

import com.myapp.domain.Continent;
import com.myapp.repository.ContinentRepository;

import org.hibernate.annotations.common.util.impl.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Continent.
 */
@Service
@CacheConfig(cacheNames = "countries")
@Transactional
public class ContinentService {

    private final Logger log = LoggerFactory.getLogger(ContinentService.class);
    
    private final ContinentRepository continentRepository;

    public ContinentService(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    /**
     * Save a continent.
     *
     * @param continent the entity to save
     * @return the persisted entity
     */
    public Continent save(Continent continent) {
        log.debug("Request to save Continent : {}", continent);
        Continent result = continentRepository.save(continent);
        return result;
    }

    /**
     *  Get all the continents.
     *  
     *  @return the list of entities
     */
    @Cacheable
    @Transactional(readOnly = true)
    public List<Continent> findAll() {
        log.info("Request to get all Continents");
        List<Continent> result = continentRepository.findAll();

        return result;
    }

    /**
     *  Get one continent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Continent findOne(Long id) {
        log.debug("Request to get Continent : {}", id);
        Continent continent = continentRepository.findOne(id);
        return continent;
    }

    /**
     *  Delete the  continent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Continent : {}", id);
        continentRepository.delete(id);
    }
    
    /**
     * Get data from procedure
     * 
     * @param inContinentName the name passed in query
     */
    
    public String getContinentByName(String inContinentName) {        
    	List <String> names =  (List<String>)(Object) continentRepository.getContinentByName(inContinentName);
        //List <String> names =  continentRepository.getContinentByName();
        String continentName = null;
        
        for (String name : names){
        	continentName = name;
        }
        
        return continentName;
    }     
}

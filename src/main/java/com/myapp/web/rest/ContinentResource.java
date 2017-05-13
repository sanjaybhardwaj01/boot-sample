package com.myapp.web.rest;

import com.myapp.domain.Continent;
import com.myapp.service.ContinentService;
import com.myapp.web.rest.util.HeaderUtil;
import com.myapp.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Continent.
 */
@RestController
@RequestMapping("/api")
public class ContinentResource {

    private final Logger log = LoggerFactory.getLogger(ContinentResource.class);

    private static final String ENTITY_NAME = "continent";
        
    private final ContinentService continentService;

    public ContinentResource(ContinentService continentService) {
        this.continentService = continentService;
    }

    /**
     * POST  /continents : Create a new continent.
     *
     * @param continent the continent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new continent, or with status 400 (Bad Request) if the continent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/continents")    
    public ResponseEntity<Continent> createContinent(@RequestBody Continent continent) throws URISyntaxException {
        log.debug("REST request to save Continent : {}", continent);
        if (continent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new continent cannot already have an ID")).body(null);
        }
        Continent result = continentService.save(continent);
        return ResponseEntity.created(new URI("/api/continents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /continents : Updates an existing continent.
     *
     * @param continent the continent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated continent,
     * or with status 400 (Bad Request) if the continent is not valid,
     * or with status 500 (Internal Server Error) if the continent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/continents")
    public ResponseEntity<Continent> updateContinent(@RequestBody Continent continent) throws URISyntaxException {
        log.debug("REST request to update Continent : {}", continent);
        if (continent.getId() == null) {
            return createContinent(continent);
        }
        Continent result = continentService.save(continent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, continent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /continents : get all the continents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of continents in body
     */
    @GetMapping("/continents")
    public List<Continent> getAllContinents() {
        log.debug("REST request to get all Continents");
        return continentService.findAll();
    }

    /**
     * GET  /continents/:id : get the "id" continent.
     *
     * @param id the id of the continent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the continent, or with status 404 (Not Found)
     */
    @GetMapping("/continents/{id}")
    public ResponseEntity<Continent> getContinent(@PathVariable Long id) {
        log.debug("REST request to get Continent : {}", id);
        Continent continent = continentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(continent));
    }

    /**
     * DELETE  /continents/:id : delete the "id" continent.
     *
     * @param id the id of the continent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/continents/{id}")
    public ResponseEntity<Void> deleteContinent(@PathVariable Long id) {
        log.debug("REST request to delete Continent : {}", id);
        continentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  Query on procedure
     *
     * @param inContinentName the continent Name passed in the query
     * @return the ResponseEntity with status 200 (OK) and with body the continent, or with status 404 (Not Found)
     */
    @GetMapping("/continent-query/{inContinentName}")
    public ResponseEntity<Continent> getContinentbyName(@PathVariable String inContinentName) {
        log.debug("REST request to get getContinentbyName : {}", inContinentName);
        Continent continent = new Continent();
        continent.setContinentName(continentService.getContinentByName(inContinentName));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(continent));
    }

}

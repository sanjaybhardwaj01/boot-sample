package com.myapp.web.rest;

import com.myapp.domain.Country;

import com.myapp.repository.CountryRepository;
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
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "country";
        
    private final CountryRepository countryRepository;

    public CountryResource(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * POST  /countries : Create a new country.
     *
     * @param country the country to create
     * @return the ResponseEntity with status 201 (Created) and with body the new country, or with status 400 (Bad Request) if the country has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new country cannot already have an ID")).body(null);
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countries : Updates an existing country.
     *
     * @param country the country to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated country,
     * or with status 400 (Bad Request) if the country is not valid,
     * or with status 500 (Internal Server Error) if the country couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/countries")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
            return createCountry(country);
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, country.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countries : get all the countries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countries in body
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        log.debug("REST request to get all Countries");
        List<Country> countries = countryRepository.findAll();
        return countries;
    }

    /**
     * GET  /countries/:id : get the "id" country.
     *
     * @param id the id of the country to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the country, or with status 404 (Not Found)
     */
    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(country));
    }

    /**
     * DELETE  /countries/:id : delete the "id" country.
     *
     * @param id the id of the country to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
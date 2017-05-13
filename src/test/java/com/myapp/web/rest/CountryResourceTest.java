package com.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

//import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.myapp.Application;
import com.myapp.security.JwtTokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CountryResourceTest {
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private MockMvc mockMvc;
	
	private Device device;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));
	
	/* Only if Secuity is enabled */
	private RequestPostProcessor addBearerToken(final String username, String... authorities) {
        return mockRequest -> {
            // Create JWT Token
        	
        	GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
    		UserDetails userDetails = (UserDetails)new User(username, "", Arrays.asList(authority));
    		
            String jwttoken = jwtTokenUtil.generateToken(userDetails,device);
            System.out.println(" Token "+jwttoken);
            // Set Authorization header to use Bearer
            mockRequest.addHeader("Authorization",jwttoken);
            return mockRequest;
        };
    }
	
	@Before
    public void setup() throws Exception {
		/***  Without Security 
        this.mockMvc = webAppContextSetup(webApplicationContext).build();        
        ***/ 
        /* With Security */		
        this.mockMvc = webAppContextSetup(webApplicationContext)
        								  .apply(springSecurity())
        								   .build(); 
        
    }

	@Test
	public void testGetAllCountries() throws Exception {
		
		RequestPostProcessor bearerToken = addBearerToken("admin", "ROLE_ADMIN"); //For Security
		mockMvc.perform(get("/api/countries").with(bearerToken))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].countryName", is("Singapore")));
	}

}
package com.mangofactory.swagger.spring.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.sample.exception.NotFoundException;

@Controller
@RequestMapping("/petsregex")
@Api(value="", description="Operations about pets with regex")
public class PetsWithRegexController {

//	@RequestMapping(value="/name/{petName:[^\\.]*}{ext:\\.?[a-z0-9]*}", method=RequestMethod.GET)
//	@ApiOperation(value = "Find a pet by name", notes = "Returns a pet when petName contains the string \"valid\" " +
//			"Otherwise simulates API error conditions", responseClass = "com.mangofactory.swagger.spring.test.Pet", multiValueResponse=true)
//	@ApiErrors(NotFoundException.class)
//	public List<Pet> findByName( @PathVariable("petName") String name, @PathVariable("ext") String extension ) throws NotFoundException
//	{
//		if ( name != null && name.contains("valid") )
//			return new ArrayList<Pet>();
//		else
//			throw new NotFoundException(404, "No pet records found for given name: \"" + name + "\"");
//				
//	}
//
//	@RequestMapping(value="/{id}/status{ext:\\.?[a-z0-9]*}", method=RequestMethod.GET)
//	@ApiOperation(value = "Get the status of a pet", responseClass = "java.lang.String")
//	@ApiErrors(NotFoundException.class)
//	@ResponseBody
//	public String getPetStatus( @PathVariable("id") String id, @PathVariable("ext") String ext ) throws NotFoundException
//	{
//		return "Pet #" + id + " is happily playing with " + ext ;
//	}
	
    @RequestMapping(value="/{id:[^\\.]*?}{ext:\\.?[a-z0-9]*}", method=RequestMethod.PUT)
    @ApiOperation(value = "Update a pet", responseClass = "com.mangofactory.swagger.spring.test.Pet", multiValueResponse=false)
    @ApiErrors(NotFoundException.class)
    @ResponseBody
    public Pet updatePet( @PathVariable("id") String id, @PathVariable("ext") String ext, 
                    @RequestBody(required=false) Pet petToUpdate ) throws NotFoundException
    {
        if ( petToUpdate == null )
                throw new NotFoundException(HttpServletResponse.SC_BAD_REQUEST, "Pet object to update is required as request body");
        
        petToUpdate.setName("Updated on " + new Date());
        return petToUpdate;
    }
}

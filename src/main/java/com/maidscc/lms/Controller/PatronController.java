package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.Patron;
import com.maidscc.lms.Service.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@Tag(name = "Patron API")
@SecurityRequirement(name = "basicAuth")
public class PatronController {
    private final PatronService patronService;
    @Autowired
    private PatronController(PatronService patronService){
        this.patronService = patronService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve All Patrons", description = "Retrieve a list of patrons")
    @ApiResponse(responseCode = "200",description = "Successfully retrieve list of patrons")
    public List<Patron> getAllPatrons(){
        return patronService.getAllPatrons();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a patron by ID",description = "Retrieve a patron with specific ID")
    @ApiResponse(responseCode = "200",description = "Successfully retrieved the patron")
    @ApiResponse(responseCode = "404",description = "Book Not Found")
    public Patron getPatronById(@PathVariable Integer id){
        return patronService.getPatronById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Patron")
    @ApiResponse(responseCode = "201",description ="Successfully create a new Patron")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Patron to be added",required = true)
    public Patron addPatron(@RequestBody Patron patron){
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update a patron", description = "Update an existing patron's details")
    @ApiResponse(responseCode = "200",description = "patron updated successfully")
    @ApiResponse(responseCode = "404",description = "Not Found")
    public Patron updatePatron(@PathVariable Integer id,@RequestBody Patron patron){
        return patronService.updatePatron(id,patron);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete a patron", description = "delete a patron with a specified ID")
    @ApiResponse(responseCode = "200",description = "patron deleted successfully")
    @ApiResponse(responseCode = "404",description = "Not Found")
    public void deletePatronById(@PathVariable Integer id){
        patronService.deletePatronById(id);
    }
}
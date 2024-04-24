package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.Patron;
import com.maidscc.lms.Service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;
    @Autowired
    private PatronController(PatronService patronService){
        this.patronService = patronService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Patron> getAllPatrons(){
        return patronService.getAllPatrons();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Patron getPatronById(@PathVariable Integer id){
        return patronService.getPatronById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patron addPatron(@RequestBody Patron patron){
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patron updatePatron(@PathVariable Integer id,@RequestBody Patron patron){
        return patronService.updatePatron(id,patron);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatronById(@PathVariable Integer id){
        patronService.deletePatronById(id);
    }
}

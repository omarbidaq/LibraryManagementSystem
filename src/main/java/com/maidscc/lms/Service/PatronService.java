package com.maidscc.lms.Service;

import com.maidscc.lms.Entity.Patron;
import com.maidscc.lms.ExceptionHandlers.ResourceNotFoundException;
import com.maidscc.lms.Repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatronService {
    private final PatronRepository patronRepository;
    @Autowired
    public PatronService(PatronRepository patronRepository){
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons(){
        return patronRepository.findAll();
    }
    public Patron getPatronById(Integer id){
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Existing Patron with ID : " + id));
    }

    @Transactional
    public Patron addPatron(Patron patron){
        if (patron != null){
            return patronRepository.save(patron);
        }else {
            throw new ResourceNotFoundException("Bad Request -> You are sending empty fields");
        }
    }

    @Transactional
    public Patron updatePatron(Integer id,Patron updatedPatron){
        Patron patron = patronRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Patron with ID: " + id));

        patron.setId(id);
        patron.setName(updatedPatron.getName());
        patron.setContactInfo(updatedPatron.getContactInfo());

        return patronRepository.save(patron);
    }

    @Transactional
    public void deletePatronById(Integer id){
        patronRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Patron with ID: " + id));

        patronRepository.deleteById(id);
    }
}

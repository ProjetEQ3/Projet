package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.repositories.EmployeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeurService {

    private final EmployeurRepository employeurRepository;

    @Autowired
    public EmployeurService(EmployeurRepository employeurRepository) {
        this.employeurRepository = employeurRepository;
    }

    // database operations here

}

package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.repositories.GestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionnaireService {

    private final GestionnaireRepository gestionnaireRepository;

    @Autowired
    public GestionnaireService(GestionnaireRepository gestionnaireRepository) {
        this.gestionnaireRepository = gestionnaireRepository;
    }

}

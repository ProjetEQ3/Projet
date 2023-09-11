package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final StudentRepository studentRepository;

    @Autowired
    public EtudiantService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // database operations here

    public Student createEtudiant(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllEtudiants() {
        return studentRepository.findAll();
    }

    public Optional<Student> getEtudiantByID(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateEtudiant(Long id, Student updatedStudent) {
        Optional<Student> existingEtudiant = studentRepository.findById(id);
        if(existingEtudiant.isPresent()) {
            Student student = existingEtudiant.get();

            student.setNom(updatedStudent.getNom());
            student.setPrenom(updatedStudent.getPrenom());
            student.setAdresseCourriel(updatedStudent.getAdresseCourriel());
            student.setMotDePasse(updatedStudent.getMotDePasse());
            student.setMatricule(updatedStudent.getMatricule());
            student.setDepartment(updatedStudent.getDepartment());

            return studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student with ID " + id + " does not exist.");
        }
    }

    public void deleteEtudiant(Long id) {
        studentRepository.deleteById(id);
    }

}

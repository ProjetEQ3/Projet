package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.cvFile.CvFile;

public interface StudentSummary {
    Long getId();
    String getEmail();
    String getFirstName();
    String getLastName();
    Role getRole();
    String getMatricule();
    Department getDepartment();
    CvFile getCvFile();
}

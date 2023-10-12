package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Role;

public interface StudentSummary {
    Long getId();
    String getEmail();
    String getFirstName();
    String getLastName();
    Role getRole();
    String getMatricule();
    Department getDepartment();
}

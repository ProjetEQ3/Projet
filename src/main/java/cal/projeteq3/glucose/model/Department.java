package cal.projeteq3.glucose.model;

public enum Department {
    _410B0("Techniques de comptabilité et de gestion"),
    _241A1("Techniques de génie mécanique"),
    _420B0("Techniques de l’informatique"),
    _210AA("Techniques de laboratoire : biotechnologies"),
    _144A1("Techniques de physiothérapie"),
    _310A0("Techniques policières"),
    _145A0("Techniques de santé animale"),
    _388A0("Techniques de travail social"),
    _140C0("Technologie d’analyses biomédicales"),
    _243C0("Technologie de l’électronique industrielle"),
    _243BA("Technologie de l’électronique : Télécommunication"),
    _241D0("Technologie de maintenance industrielle"),
    _243A0("Technologie de systèmes ordinés"),
    _221B0("Technologie du génie civil")
    ;

    private final String department;

    Department(String department) {
        this.department = department;
    }

//    @Override
//    public String toString() {
//        return "department: " + department;
//    }
}

package cal.projeteq3.glucose.model;

//<option value="_410B0">410.B0 - Techniques de comptabilité et de gestion</option>
//<option value="_241A1">241.A1 - Techniques de génie mécanique</option>
//<option value="_420B0">420.B0 - Techniques de l’informatique</option>
//<option value="_210AA">210.AA - Techniques de laboratoire : biotechnologies</option>
//<option value="_144A1">144.A1 - Techniques de physiothérapie</option>
//<option value="_310A0">310.A0 - Techniques policières</option>
//<option value="_145A0">145.A0 - Techniques de santé animale</option>
//<option value="_388A0">388.A0 - Techniques de travail social</option>
//<option value="_140C0">140.C0 - Technologie d’analyses biomédicales</option>
//<option value="_243C0">243.C0 - Technologie de l’électronique industrielle</option>
//<option value="_243BA">243.BA - Technologie de l’électronique : Télécommunication</option>
//<option value="_241D0">241.D0 - Technologie de maintenance industrielle</option>
//<option value="_243A0">245.A0 - Technologie de systèmes ordinés</option>
//<option value="_221B0">221.B0 - Technologie du génie civil</option>
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

    @Override
    public String toString() {
        return "department: "+ this + "\t" + department;
    }
}

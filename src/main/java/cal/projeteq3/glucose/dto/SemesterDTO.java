package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Semester;

public class SemesterDTO {
    private Semester.Session session;
    private int year;

    public SemesterDTO(Semester semester) {
        this.session = semester.getSession();
        this.year = semester.getYear();
    }

    public Semester toEntity(){
        return Semester.builder()
                .session(this.session)
                .year(this.year)
                .build();
    }
}

package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Semester;
import lombok.Getter;

@Getter
public class SemesterDTO {

    private Semester.Season season;
    private int year;

    public SemesterDTO(Semester semester) {
        this.season = semester.getSeason();
        this.year = semester.getYear();
    }

    public Semester toEntity() {
        return Semester.builder()
                .season(season)
                .year(year)
                .build();
    }
}

package cal.projeteq3.glucose.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Semester {

    @Enumerated(EnumType.STRING)
    private Season season;
    private int year;

    public enum Season {
        WINTER, SUMMER, FALL
    }

    public Semester(LocalDate localDate) {

        if(localDate == null) {
            throw new IllegalArgumentException("the localdate provided is null.");
        }

        season = getSeasonFromDate(localDate);
        year = localDate.getYear();
    }

    private Season getSeasonFromDate(LocalDate localDate) {
        int month = localDate.getMonthValue();
        if(month <= 4) {
            return Season.WINTER;
        } else if(month <= 8) {
            return Season.SUMMER;
        } else {
            return Season.FALL;
        }
    }

    public Semester previousSemester() {
        return switch (season) {
            case WINTER -> new Semester(Season.FALL, year - 1);
            case SUMMER -> new Semester(Season.WINTER, year);
            case FALL -> new Semester(Season.SUMMER, year);
        };
    }

    public Semester nextSemester() {
        return switch (season) {
            case WINTER -> new Semester(Season.SUMMER, year);
            case SUMMER -> new Semester(Season.FALL, year);
            case FALL -> new Semester(Season.WINTER, year + 1);
        };
    }

}

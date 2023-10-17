package cal.projeteq3.glucose.model;

import java.time.LocalDate;

public class Semester {

    private Session session;
    private int year;

    public enum Session {
        WINTER, SUMMER, FALL
    }

    public Semester(LocalDate localDate) {

        if(localDate == null) {
            throw new IllegalArgumentException("the localdate provided is null.");
        }

        session = getSessionFromDate(localDate);
        year = localDate.getYear();
    }

    private Session getSessionFromDate(LocalDate localDate) {
        int month = localDate.getMonthValue();

        if(month >= 1 && month <= 4) {
            return Session.WINTER;
        } else if(month >= 5 && month <= 8) {
            return Session.SUMMER;
        } else if(month >= 9 && month <= 12) {
            return Session.FALL;
        } else {
            throw new IllegalArgumentException("you dont know how dates work do you?");
        }

    }

    public Session getSession() {
        return this.session;
    }

    public int getYear() {
        return this.year;
    }

}
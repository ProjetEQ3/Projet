
class SectionDTO {
    agreementLevels = [];
    internshipPerformance ;
    welcomeInternBackStatus ;
    discussedWithIntern = false;
    comment = "";

    constructor() {
        this.reset();
    }

    reset() {
        this.agreementLevels = [];
        this.internshipPerformance = undefined;
        this.welcomeInternBackStatus = undefined;
        this.discussedWithIntern = false;
        this.comment = "";
    }

    init(section) {
        if(section.agreementLevels) this.agreementLevels = section.agreementLevels;
        else this.agreementLevels = [];
        if(section.internshipPerformance) this.internshipPerformance = section.internshipPerformance;
        else this.internshipPerformance = undefined;
        if(section.welcomeInternBackStatus) this.welcomeInternBackStatus = section.welcomeInternBackStatus;
        else this.welcomeInternBackStatus = undefined;
        if(section.discusssedWithIntern) this.discusssedWithIntern = section.discusssedWithIntern;
        else this.discusssedWithIntern = false;
        if(section.comment) this.comment = section.comment;
        else this.comment = "";
    }
}

export default SectionDTO;
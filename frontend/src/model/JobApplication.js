import JobOffer from "./JobOffer";
import Student from "./Student";

class JobApplication {
    id
    student
    jobOffer
    jobApplicationState
    year
    season

    constructor() {
        this.reset()
    }

    reset() {
        this.id = 0
        this.student = new Student()
        this.jobOffer = new JobOffer()
        this.state = ""
        this.year = 0
        this.season = ""
    }

    init(jobApplication, student) {
        if (jobApplication.id) this.id = jobApplication.id
        else delete this.id
        if (jobApplication.jobOffer) this.jobOffer = jobApplication.jobOffer
        else delete this.jobOffer
        if (jobApplication.jobApplicationState) this.jobApplicationState = jobApplication.jobApplicationState
        else delete this.state
        if (jobApplication.year) this.year = jobApplication.year
        else delete this.year
        if (jobApplication.season) this.season = jobApplication.season
        else delete this.season
        this.student = student
    }
}

export default JobApplication
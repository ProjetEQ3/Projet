// private Long id;
// private StudentDTO student;
// private JobOfferDTO jobOffer;
// private JobApplicationState jobApplicationState;
// private Semester semester;
// private List<AppointmentDTO> appointments;

class Application{
    id
    student
    jobOffer
    jobApplicationState
    semester
    appointments

    constructor(){
        this.reset()
    }

    reset(){
        this.id = 0
        this.student = null
        this.jobOffer = null
        this.jobApplicationState = null
        this.semester = null
        this.appointments = []
    }

    init(application){
        if(application.id) this.id = application.id
        else delete this.id
        if(application.student) this.student = application.student
        else delete this.student
        if(application.jobOffer) this.jobOffer = application.jobOffer
        else delete this.jobOffer
        if(application.jobApplicationState) this.jobApplicationState = application.jobApplicationState
        else delete this.jobApplicationState
        if(application.semester) this.semester = application.semester
        else delete this.semester
        if(application.appointments) this.appointments = application.appointments
        else delete this.appointments
    }
}

export default Application
class Appointment {
    id
    jobApplication
    appointmentDate
    chosen

    constructor(){
        this.reset()
    }

    reset(){
        this.id = 0
        this.jobApplication = null
        this.appointmentDate = ""
        this.chosen = false
    }

    init(appointment){
        if(appointment.id) this.id = appointment.id
        else delete this.id
        if(appointment.jobApplication) this.jobApplication = appointment.jobApplication
        else delete this.jobApplication
        if(appointment.appointmentDate) this.appointmentDate = appointment.appointmentDate
        else delete this.appointmentDate
        if(appointment.chosen !== undefined) this.chosen = appointment.chosen
        else delete this.chosen
    }

}

export default Appointment
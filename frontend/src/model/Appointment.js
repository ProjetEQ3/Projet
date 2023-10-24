class Appointment {
    id
    jobApplication
    appointmentDate
    isChosen

    constructor(){
        this.reset()
    }

    reset(){
        this.id = 0
        this.jobApplication = null
        this.appointmentDate = ""
        this.isChosen = false
    }

    init(appointment){
        if(appointment.id) this.id = appointment.id
        else delete this.id
        if(appointment.jobApplication) this.jobApplication = appointment.jobApplication
        else delete this.jobApplication
        if(appointment.appointmentDate) this.appointmentDate = appointment.appointmentDate
        else delete this.appointmentDate
        if(appointment.isChosen) this.isChosen = appointment.isChosen
        else delete this.isChosen
    }

}

export default Appointment
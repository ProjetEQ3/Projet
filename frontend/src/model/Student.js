class Student {
    id
    firstName
    lastName
    email
    matricule
    department
    state

    constructor(){
        this.reset()
    }

    reset(){
        this.id = 0
        this.firstName = ""
        this.lastName = ""
        this.email = ""
        this.matricule = ""
        this.department = ""
        this.state = ""
    }

    init(student){
        if(student.id) this.id = student.id
        else delete this.id
        if(student.firstName) this.firstName = student.firstName
        else delete this.firstName
        if(student.lastName) this.lastName = student.lastName
        else delete this.lastName
        if(student.email) this.email = student.email
        else delete this.email
        if(student.matricule) this.matricule = student.matricule
        else delete this.matricule
        if(student.department) this.department = student.department
        else delete this.department
        if(student.state) this.state = student.state
        else delete this.state
    }
}

export default Student
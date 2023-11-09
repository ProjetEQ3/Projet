class Student {
    id
    firstName
    lastName
    email
    matricule
    department
    studentState

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
        this.studentState = ""
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
        if(student.studentState) this.studentState = student.studentState
        else delete this.studentState
    }
}

export default Student
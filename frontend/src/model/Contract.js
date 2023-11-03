class Contract{
    id
    employerId
    studentId
    jobOfferId
    employerSignature
    studentSignature
    managerSignature
    studentName
    jobOfferName
    jobOfferCompany
    data
    isComplete

    constructor(){
        this.reset()
    }

    reset(){
        this.id = 0
        this.employerId = null
        this.studentId = null
        this.jobOfferId = null
        this.employerSignature = ""
        this.studentSignature = ""
        this.managerSignature = ""
        this.studentName = ""
        this.jobOfferName = ""
        this.jobOfferCompany = ""
        this.data = ""
        this.isComplete = false
    }

    init(contract){
        if(contract.id) this.id = contract.id
        else delete this.id
        if(contract.employer) this.employer = contract.employer
        else delete this.employer
        if(contract.student) this.student = contract.student
        else delete this.student
        if(contract.jobOffer) this.jobOffer = contract.jobOffer
        else delete this.jobOffer
        if(contract.employerSignature) this.employerSignature = contract.employerSignature
        else delete this.employerSignature
        if(contract.studentSignature) this.studentSignature = contract.studentSignature
        else delete this.studentSignature
        if(contract.managerSignature) this.managerSignature = contract.managerSignature
        else delete this.managerSignature
        if(contract.studentName) this.studentName = contract.studentName
        else delete this.studentName
        if(contract.jobOfferName) this.jobOfferName = contract.jobOfferName
        else delete this.jobOfferName
        if(contract.jobOfferCompany) this.jobOfferCompany = contract.jobOfferCompany
        else delete this.jobOfferCompany
        if(contract.data) this.data = contract.data
        else delete this.data
        if(contract.isComplete) this.isComplete = contract.isComplete
        else this.isComplete = false
    }
}

export default Contract
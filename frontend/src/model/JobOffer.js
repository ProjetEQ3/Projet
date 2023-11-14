class JobOffer{
	id
	title
	department
	location
	description
	salary
	startDate
	duration
	expirationDate
	jobOfferState
	hoursPerWeek
	dueDate
	nbOfCandidates
	hasApplied
	refusReason

	constructor(){
		this.reset()
	}

	init(jobOffer){
		if(jobOffer.id) this.id = jobOffer.id
		else delete this.id
		if(jobOffer.title) this.title = jobOffer.title
		else delete this.title
		if(jobOffer.positionTitle) this.positionTitle = jobOffer.positionTitle
		else delete this.positionTitle
		if(jobOffer.department) this.department = jobOffer.department
		else delete this.department
		if(jobOffer.location) this.location = jobOffer.location
		else delete this.location
		if(jobOffer.description) this.description = jobOffer.description
		else delete this.description
		if(jobOffer.salary) this.salary = jobOffer.salary
		else delete this.salary
        if(jobOffer.startDate) this.startDate = jobOffer.startDate
        else delete this.startDate
        if(jobOffer.duration) this.duration = jobOffer.duration
        else delete this.duration
		if(jobOffer.dueDate) this.dueDate = jobOffer.dueDate
		else delete this.dueDate
		if(jobOffer.expirationDate) this.expirationDate = jobOffer.expirationDate
		else delete this.expirationDate
		if(jobOffer.jobOfferState) this.jobOfferState = jobOffer.jobOfferState
		else delete this.jobOfferState
		if(jobOffer.hoursPerWeek) this.hoursPerWeek = jobOffer.hoursPerWeek
		else delete this.hoursPerWeek
    	if(jobOffer.startDate) this.startDate = jobOffer.startDate
		else delete this.startDate
		if(jobOffer.duration) this.duration = jobOffer.duration
		else delete this.duration
		if(jobOffer.nbOfCandidates) this.nbOfCandidates = jobOffer.nbOfCandidates
		else delete this.nbOfCandidates
		if(jobOffer.hasApplied) this.hasApplied = jobOffer.hasApplied
		else this.hasApplied = false
		if(jobOffer.refusReason) this.refusReason = jobOffer.refusReason
		else delete this.refusReason
	}

	reset(){
		this.id = 0
		this.title = ''
		this.department = ''
		this.location = ''
		this.description = ''
		this.salary = 0
		this.startDate = ''
		this.duration = 0
		this.expirationDate = ''
		this.jobOfferState = ''
		this.hoursPerWeek = 0
		this.dueDate = ''
		this.nbOfCandidates = 0
		this.hasApplied = false
		this.refusReason = ''
	}
}

export default JobOffer
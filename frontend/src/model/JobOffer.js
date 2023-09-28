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
		if(jobOffer.location) this.location = jobOffer.Location
		else delete this.location
		if(jobOffer.description) this.description = jobOffer.description
		else delete this.description
		if(jobOffer.salary) this.salary = jobOffer.salary
		else delete this.salary
		if(jobOffer.dueDate) this.dueDate = jobOffer.dueDate
		else delete this.dueDate
		if(jobOffer.expirationDate) this.expirationDate = jobOffer.expirationDate
		else delete this.expirationDate
		if(jobOffer.jobOfferState) this.jobOfferState = jobOffer.jobOfferState
		else delete this.jobOfferState
	}

	reset(){
		this.id = undefined
		this.title = undefined
		this.positionTitle = undefined
		this.department = undefined
		this.location = undefined
		this.description = undefined
		this.salary = undefined
		this.formation = undefined
		this.dueDate = undefined
		this.expirationDate = undefined
		this.jobOfferState = undefined
	}

}

export default JobOffer


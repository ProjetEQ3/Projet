class JobOffer{
	id
	PositionTitle
	Department
	Location
	Description
	Salary
	Formation
	dueDate
	ExpirationDate

	constructor(){
		this.reset()
	}

	init(jobOffer){
		if(jobOffer.id) this.id = jobOffer.id
		else delete this.id
		if(jobOffer.PositionTitle) this.PositionTitle = jobOffer.PositionTitle
		else delete this.PositionTitle
		if(jobOffer.Department) this.Department = jobOffer.Department
		else delete this.Department
		if(jobOffer.Location) this.Location = jobOffer.Location
		else delete this.Location
		if(jobOffer.Description) this.Description = jobOffer.Description
		else delete this.Description
		if(jobOffer.Salary) this.Salary = jobOffer.Salary
		else delete this.Salary
		if(jobOffer.Formation) this.Formation = jobOffer.Formation
		else delete this.Formation
		if(jobOffer.dueDate) this.dueDate = jobOffer.dueDate
		else delete this.dueDate
		if(jobOffer.ExpirationDate) this.ExpirationDate = jobOffer.ExpirationDate
		else delete this.ExpirationDate
	}

	reset(){
		this.id = undefined
		this.PositionTitle = undefined
		this.Department = undefined
		this.Location = undefined
		this.Description = undefined
		this.Salary = undefined
		this.Formation = undefined
		this.dueDate = undefined
		this.ExpirationDate = undefined
	}

}

export default JobOffer


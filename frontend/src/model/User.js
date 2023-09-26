class User{
	id
	firstName
	lastName
	email
	password
	isLoggedIn = false
	cvFile
	role
	department
	errors = []

	constructor(){}

	init(user){
		if(user.id) this.id = user.id
		else delete this.id
		if(user.firstName) this.firstName = user.firstName
		else delete this.firstName
		if(user.lastName) this.lastName = user.lastName
		else delete this.lastName
		if(user.email) this.email = user.email
		else delete this.email
		if(user.password) this.password = user.password
		else delete this.password
		if(user.isLoggedIn) this.isLoggedIn = user.isLoggedIn
		else this.isLoggedIn = false
		if(user.cvFile) this.cvFile = user.cvFile
		else delete this.cvFile
		if(user.role) this.role = user.role
		else delete this.role
		if(user.department) this.department = user.department
		else delete this.department
		if(user.errors?.length > 0) this.errors = user.errors
		else delete this.errors
	}

}

export default User

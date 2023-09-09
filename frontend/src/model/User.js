class User{
	id
	firstName
	lastName
	email
	password
	isLoggedIn = false
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
		else delete this.isLoggedIn
		if(user.errors?.length > 0) this.errors = user.errors
		else delete this.errors
	}

}

export default User

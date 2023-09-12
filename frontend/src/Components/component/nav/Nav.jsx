import './Nav.css'
import {NavLink} from "react-router-dom"

const Nav = ({}) => {
	return (
		<nav id="App-nav">
			<ul className={"menu"}>
				<li><NavLink activeclassname="active" to="/">Home</NavLink></li>
				<li><NavLink activeclassname="active" to="auth">Auth</NavLink></li>
				<li><NavLink activeclassname="active" to="clients">Clients</NavLink></li>
			</ul>
		</nav>
	)
}

export default Nav

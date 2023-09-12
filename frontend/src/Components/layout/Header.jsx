/*import './Header.css'*/
import PropTypes from "prop-types"
import {NavLink} from "react-router-dom"
import Nav from "../component/nav/Nav";

const Header = ({title, user}) => {
	return (
		<header className="App-header">
			{/*<NavLink to={"/"}><img id="App-logo" src="/img/Webim.png" alt="logo"/></NavLink>TODO: ici le logo*/}
			<h1 id="App-title">{title}</h1>
			{/*<div id="LoginInfoHeader"><LoginInfo user={user}/></div>TODO: ici login information*/}
			<Nav/>
		</header>
	)
}

Header.defaultProps = {
	title: 'Header'
}

Header.propTypes = {
	title: PropTypes.string.isRequired
}

export default Header

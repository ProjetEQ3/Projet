import "./SideMenu.css"
import {NavLink} from "react-router-dom"
import React from "react"

const SideMenu = ({links, side}) => {
	return (
		<>
			<div id="sideMenu" className={side + "Menu"}>
				<h4>Side Menu</h4>
				<ul className="menu">
					{links.map((link, index) => {
						return (
							<li key={index}>
								<NavLink key={link.index} to={link.path}>{link.name}</NavLink>
							</li>
						)
					})}
				</ul>
			</div>
		</>
	)
}

SideMenu.defaultProps = {
	links: [
		{
			name: "Home",
			path: "/"
		},
		{
			name: "Login",
			path: "/login"
		}
	]
}

export default SideMenu

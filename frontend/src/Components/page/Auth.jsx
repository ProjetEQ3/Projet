import React from "react"
import {Navigate, NavLink, Route, Routes} from "react-router-dom"
import LogIn from "../component/auth/LogIn"
import LoginInfo from "../component/auth/LoginInfo"
import LogOut from "../component/auth/LogOut"
import PageNotFound from "./PageNotFound"

const Auth = ({}) => {
	return (
		<div className="mainContent">
			<>
				<NavLink activeclassname="active" to="loginInfo">Go to loginInfo</NavLink>
				<br></br>
				<NavLink activeclassname="active" to="login">Go to login</NavLink>
				<br></br>
				<NavLink activeclassname="active" to="logout">Go to logout</NavLink>
				<br></br>
			</>
			<Routes>
				<Route
					path="/"
					element={
						<Navigate to="LogIn"/>
					}
				/>
				<Route
					path="loginInfo"
					element={
						<LoginInfo/>
					}
				/>
				<Route
					path="login"
					element={
						<LogIn/>
					}
				/>
				<Route
					path="logout"
					element={
						<LogOut/>
					}
				/>
				<Route path="*" element={<PageNotFound/>}/>
			</Routes>
		</div>
	)
}

export default Auth
import {Navigate, NavLink, Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import React from "react";
import ClientComponent1 from "../component/Client/ClientComponent1";
import ClientComponent2 from "../component/Client/ClientComponent2";
import ClientComponent3 from "../component/Client/ClientComponent3";
import ClientComponent4 from "../component/Client/ClientComponent4";

const Clients = ({}) => {
	return (
		<div className="mainContent">
			<>
				<NavLink activeclassname="active" to="componenet1">Go to componenet 1</NavLink>
				<br></br>
				<NavLink activeclassname="active" to="componenet2">Go to componenet 2</NavLink>
				<br></br>
				<NavLink activeclassname="active" to="componenet3">Go to componenet 3</NavLink>
				<br></br>
				<NavLink activeclassname="active" to="componenet4">Go to componenet 4</NavLink>
			</>
			<Routes>
				<Route
					path="componenet1"
					element={
						<ClientComponent1/>
					}
				/>
				<Route
					path="componenet2"
					element={
						<ClientComponent2/>
					}
				/>
				<Route
					path="componenet3"
					element={
						<ClientComponent3/>
					}
				/>
				<Route
					path="componenet4"
					element={
						<ClientComponent4/>
					}
				/>
				<Route path="*" element={<PageNotFound/>}/>
			</Routes>
		</div>
	)
}

export default Clients

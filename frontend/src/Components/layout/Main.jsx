import {Route, Routes} from "react-router-dom"
import Home from "../page/Home"
import Auth from "../page/Auth"
import PageNotFound from "../page/PageNotFound"
import Clients from "../page/Clients"

const Main = ({}) => {
	return (
		<main className='App-main'>
			<Routes>
				<Route
					path="/"
					element={<Home/>}
				/>
				<Route
					path="auth/*"
					element={<Auth/>
					}
				/>
				<Route
					path="clients/*"
					element={
						<Clients/>
					}
				/>
				<Route path="*" element={<PageNotFound/>}/>
			</Routes>
		</main>
	)
}

export default Main

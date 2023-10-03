import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const createContract = (addContract) => {

	const postContract = ({contract}) => {
		axiosInstance
			.post("manager/contract/create", contract)
			.then((res) => {
				toast.success(res.data.message);
				addContract(res.data);
			})
			.catch((err) => {
				toast.error(err.response.data.message);
			})
	}

			return (
				<div>
						<h1>Create Contract</h1>
				</div>
		)
}
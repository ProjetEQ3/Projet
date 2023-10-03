import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";

const createContract = (addContract) => {
	const { t } = useTranslation();

	const postContract = ({contract}) => {
		axiosInstance
			.post("manager/contract/create", contract)
			.then((res) => {
				toast.success(t("contract_created_successfully"))
				addContract(res.data);
			})
			.catch((err) => {
				toast.error(err.response.data.message);
			})
	}

			return (
				<div>
						<h1>Ajouter Contract</h1>
				</div>
		)
}
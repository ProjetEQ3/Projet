import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useEffect, useState} from "react"
import Loading from "../util/Loading"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import State from "../util/State";
import PDFPreview from "../util/PDF/PDFPreview";
import CVFile from "../../model/CvFile";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

function Cv({user, setCv, getNotificationsCount}){
	const {t} = useTranslation()
	const [isLoading, setIsLoading] = useState(false)

	const { darkMode } = useDarkMode();

	const handlePdfUpload = (e) => {
		setIsLoading(true)
		const file = e.target.files[0]
		if(file && file.type === "application/pdf"){
			const sentFile = new File([file], "cv_" + user.firstName.toLowerCase() + "_" + user.lastName.toLowerCase() + ".pdf", {type: "application/pdf"})
			const formData = new FormData()
			formData.append("file", sentFile)
			formData.append("studentId", user.id)
			if (!user?.id) return;
			axiosInstance
				.post(`/student/cv/${user.id}`, formData, {headers: {"Content-Type": "multipart/form-data"}})
				.then((response) => {
					toast.success(t('uploadedCV'))
					setCv(response.data)
					setIsLoading(false)
					getNotificationsCount()
				})
				.catch((error) => {
					toast.error(t('pushingError') + t(error.response?.data.message))
					setIsLoading(false)
				})
		}else{
			alert(t('PDFError'))
			setIsLoading(false)
		}
	}

	const handleDeletePdf = () => {
		if (!user?.id) return;
		setIsLoading(true)
		axiosInstance
			.delete(`/student/cv/${user.id}`)
			.then(() => {
				toast.success(t('deleteCV'))
				setCv(undefined)
				setIsLoading(false)
				getNotificationsCount()
			})
			.catch((error) => {
				toast.error(t('pushingError') + error.response?.data.message)
				setIsLoading(false)
			})
	}

	return (
		<div className="container">
			{isLoading ? (
				<Loading/>
			) : user.cvFile && user.cvFile.id ? (
				<>
					{
						user.cvFile.cvState === "REFUSED" &&
						<div className="text-center text-lg-start alert alert-danger py-0">
							<h1 className={"display-4 py-2"}>{t('refusedCV')}</h1>
							<h3 className={"display-6 py-0 text-center"}>{user.cvFile.refusReason}</h3>
						</div>
					}
					<div className={`row ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
						<div className="col-lg-8">
							<h2 className="text-center text-lg-start">{user.cvFile.fileName}</h2>
						</div>
						<div className="col-lg-4 d-flex my-auto mb-2 text-center justify-content-around justify-content-md-between">
							<div className="d-block col-8">
								<State state={user.cvFile.cvState}/>
							</div>
							<FontAwesomeIcon title="deleteCV" icon={faTrash} className="my-auto pe-2 fa-lg text-danger dark-hover"
							                 onClick={handleDeletePdf}/>
						</div>
						<PDFPreview file={CVFile.readBytes(user.cvFile.fileData)} contractComplete={true}/>
					</div>
				</>
			) : (
				<div>
					<h1 className="display-6">{t('uploadCV')}</h1>
					<div className="col-6 mx-auto">
						<input id="uploadCV" name="uploadCV" title="uploadCV" value="" className="form-control" type="file" accept=".pdf" onChange={handlePdfUpload}/>
					</div>
				</div>
			)
			}
		</div>
	)
}

export default Cv

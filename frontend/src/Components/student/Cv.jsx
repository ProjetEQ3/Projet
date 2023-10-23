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

function Cv({user, setCv}){
	const {t} = useTranslation()
	const [isLoading, setIsLoading] = useState(false)

	const handlePdfUpload = (e) => {
		setIsLoading(true)
		const file = e.target.files[0]
		if(file && file.type === "application/pdf"){
			const sentFile = new File([file], "cv_" + user.firstName.toLowerCase() + "_" + user.lastName.toLowerCase() + ".pdf", {type: "application/pdf"})
			const formData = new FormData()
			formData.append("file", sentFile)
			formData.append("studentId", user.id)
			axiosInstance
				.post(`/student/cv/${user.id}`, formData, {headers: {"Content-Type": "multipart/form-data"}})
				.then((response) => {
					toast.success(t('uploadedCV'))
					setCv(response.data)
					setIsLoading(false)
				})
				.catch((error) => {
					toast.error(t('pushingError') + t(error.response.data.message))
					setIsLoading(false)
				})
		}else{
			alert(t('PDFError'))
			setIsLoading(false)
		}
	}

	const handleDeletePdf = () => {
		setIsLoading(true)
		axiosInstance
			.delete(`/student/cv/${user.id}`)
			.then(() => {
				toast.success(t('deleteCV'))
				setCv(null)
				setIsLoading(false)
			})
			.catch((error) => {
				toast.error(t('pushingError') + error.response.data.message)
				setIsLoading(false)
			})
	}

	const refreshCvState = () => {
		axiosInstance
			.get(`/student/cv/${user.id}`)
			.then((response) => {
				setCv(response.data)
			})
			.catch((error) => {
			})
	}

	useEffect(() => {
		refreshCvState()
	}, [])

	return (
		<div className="container">
			{isLoading ? (
				<Loading/>
			) : user.cvFile && user.cvFile.id ? (
				<>
					<div className="row bg-white rounded">
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
						<PDFPreview file={CVFile.readBytes(user.cvFile.fileData)}/>
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

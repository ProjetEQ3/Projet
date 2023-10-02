import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useState} from "react"
import Loading from "../util/Loading"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import State from "../util/State";
import PDFPreview from "../util/PDFPreview";
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
					console.log(response)
					setCv(response.data)
					setIsLoading(false)
				})
				.catch((error) => {
					toast.error(t('pushingError') + error.message)
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
				toast.error(t('pushingError') + error.message)
				setIsLoading(false)
			})
	}

	return (
		<div className="container">
			{isLoading ? (
				<Loading/>
			) : user.cvFile.id ? (
				<>
					<div className="row bg-white rounded">
						<div className="col-8">
							<h2 className="mx-auto">{user.cvFile.fileName}</h2>
						</div>
						<div className="col-4 d-flex my-auto justify-content-end justify-content-md-between">
							<div className="d-none d-md-block">
								<State state={user.cvFile.state}/>
							</div>
							<FontAwesomeIcon icon={faTrash} className="my-auto pe-2 fa-lg text-danger dark-hover"
							                 onClick={handleDeletePdf}/>
						</div>
						<PDFPreview file={CVFile.readBytes(user.cvFile.fileData)}/>
					</div>
				</>
			) : (
				<div>
					<h1 className="display-6">{t('uploadCV')}</h1>
					<div className="col-6 mx-auto">
						<input value="" className="form-control" type="file" accept=".pdf" onChange={handlePdfUpload}/>
					</div>
				</div>
			)
			}
		</div>
	)
}

export default Cv

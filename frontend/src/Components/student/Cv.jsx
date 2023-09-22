import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useState} from "react"
import Loading from "../util/Loading"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";

function Cv({user, setCv}){
	const [isLoading, setIsLoading] = useState(false)

	const handlePdfUpload = (e) => {
		setIsLoading(true)
		const file = e.target.files[0]
		if(file && file.type === "application/pdf"){
			const formData = new FormData()
			formData.append("file", file)
			formData.append("studentId", user.id)
			axiosInstance
				.post(`/student/cv/${user.id}`, formData, {headers: {"Content-Type": "multipart/form-data"}})
				.then((response) => {
					toast.success("CV téléversé")
					console.log(response)
					setCv(response.data)
					setIsLoading(false)
				})
				.catch((error) => {
					toast.error("Erreur lors du téléversement du CV: " + error.message)
					setIsLoading(false)
				})
		}else{
			alert("Veuillez sélectionner un fichier PDF valide.")
			setIsLoading(false)
		}
	}

	const handleDeletePdf = () => {
		setIsLoading(true)
		axiosInstance
			.delete(`/student/cv/${user.id}`)
			.then(() => {
				toast.success("CV supprimé")
				setCv(null)
				setIsLoading(false)
			})
			.catch((error) => {
				toast.error("Erreur lors de la suppression du CV" + error.message)
				setIsLoading(false)
			})
	}

	const handleViewPdf = () => {
		const pdfData = atob(user.cvFile.fileData)
		const pdfBlob = new Blob([pdfData], {type: "application/pdf"})
		const url = URL.createObjectURL(pdfBlob)
		window.open(url, "_blank")
	}

	const handleDownloadPdf = () => {
		const pdfData = atob(user.cvFile.fileData)
		const pdfBlob = new Blob([pdfData], {type: "application/pdf"})
		const url = URL.createObjectURL(pdfBlob)
		const anchor = document.createElement("a")
		anchor.href = url
		anchor.download = "cv.pdf"
		anchor.click()
		URL.revokeObjectURL(url)
	}

	return (
		<div className="container">
			{isLoading ? (
				<Loading/>
			) : user.cvFile ? (
				<>
					<div className="row bg-white rounded">
						<div className="col-8">
							<h2>CV: {user.cvFile.fileName}</h2>
						</div>
						<div className="col-4 d-flex my-auto justify-content-between">
							{
								user.cvFile.cvState === 'ACCEPTED' ?
									<div className="my-auto px-2 rounded border border-success text-success">
										Accepté
									</div>:
								user.cvFile.cvState === 'REFUSED' ?
									<div className="my-auto px-2 border rounded border-danger text-danger">
										Refusé
									</div>:
									<div className="my-auto px-2 border rounded border-secondary text-secondary">
										Attente d'approbation
									</div>
							}
							<FontAwesomeIcon icon={faTrash} className="my-auto pe-2 fa-lg text-danger dark-hover" onClick={handleDeletePdf}/>

							{/*Ajouter la raison du refus du CV pour l'étudiant (le CV n'est pas load de la base de donnée)*/}
							{/*{user.cvFile.cvState === 'REFUSED' && (*/}
							{/*	<>*/}
							{/*		<div className="mx-2 my-auto text-decoration-underline"  data-bs-toggle="modal" data-bs-target="#refusedCV">*/}
							{/*			Raison du refus {user.cvFile.reason}*/}
							{/*		</div>*/}
							{/*		<div id="refusedCV" className="modal">*/}
							{/*			<div className="modal-dialog">*/}
							{/*				<div className="modal-content">*/}
							{/*					<div className="modal-header">*/}
							{/*						<h3 className="modal-title">Raison du refus</h3>*/}
							{/*					</div>*/}
							{/*					<div className="modal-body">*/}
							{/*						<p className="text-dark fw-light pt-1">{user.cvFile.reason}</p>*/}
							{/*					</div>*/}
							{/*					<div className="modal-footer">*/}
							{/*						<button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>*/}
							{/*					</div>*/}
							{/*				</div>*/}
							{/*			</div>*/}
							{/*		</div>*/}
							{/*	</>*/}
							{/*)}*/}

						</div>
					</div>
				</>
			) : (
				<div>
					<h1 className="display-6">Téléverser un CV</h1>
					<div className="col-6 mx-auto">
						<input
							value=""
							className="form-control"
							type="file"
							accept=".pdf"
							onChange={handlePdfUpload}
						/>
					</div>
				</div>
			)
			}
		</div>
	)
}

export default Cv

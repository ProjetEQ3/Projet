import serverIp, {axiosInstance} from "../../App"
import {toast} from "react-toastify"

function Cv({user, setCv}){

	const handlePdfUpload = (e) => {
		const file = e.target.files[0]
		if(file && file.type === 'application/pdf'){
			axiosInstance.post(serverIp + '/student/cv', user.id)
				.then(() => {
					toast.success('CV téléversé')
					setCv(file)
				})
				.catch(() => {
					toast.error('Erreur lors du téléversement du CV')
				})
		}else{
			alert('Veuillez sélectionner un fichier PDF valide.')
		}
	}

	const handleDeletePdf = () => {
		axiosInstance.delete(serverIp + '/student/cv', user.id)
			.then(() => {
				toast.success('CV supprimé')
				setCv(null)
			})
			.catch(() => {
				toast.error('Erreur lors de la suppression du CV')
			})
	}

	return (
		<div className="container">
			{user.cvFile ? (
				<div>
					<h2>CV:</h2>
					<p>{user.cvFile.name}</p>
					<button className="btn btn-danger" onClick={handleDeletePdf}>Delete</button>
				</div>
			) : (
				<div>
					<h1 className="display-6">Téléverser un CV</h1>
					<div className="col-6 mx-auto">
						<input value="" className="form-control" type="file" accept=".pdf" onChange={handlePdfUpload}/>
					</div>
				</div>
			)}
		</div>
	)

}

export default Cv

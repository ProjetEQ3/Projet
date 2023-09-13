import serverIp, {axiosInstance} from "../App";
import {toast} from "react-toastify";

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
		<div>
			{user.cvFile && (
				<div>
					<h2>CV:</h2>
					<p>{user.cvFile.name}</p>
					<button onClick={handleDeletePdf}>Delete</button>
				</div>
			) || (
				<div>
					<h1>Téléverser un CV</h1>
					<input
						type="file"
						accept=".pdf"
						onChange={handlePdfUpload}
					/>
				</div>
			)}
		</div>
	)
}

export default Cv

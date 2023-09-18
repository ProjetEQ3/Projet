import serverIp, { axiosInstance } from "../../App";
import { toast } from "react-toastify";
import { useState } from "react";
import Loading from "../util/Loading";

function Cv({user, setCv}) {
	const [isLoading, setIsLoading] = useState(false);

	const handlePdfUpload = (e) => {
		setIsLoading(true);
		const file = e.target.files[0];
		if (file && file.type === "application/pdf") {
			const formData = new FormData();
			formData.append("file", file);
			formData.append("studentId", user.id);
			axiosInstance
				.post(`/student/cv/${user.id}`, formData, {headers: {"Content-Type": "multipart/form-data"}})
				.then((response) => {
					toast.success("CV téléversé");
					setCv(response.data);
					setIsLoading(false);
				})
				.catch((error) => {
					toast.error("Erreur lors du téléversement du CV: " + error.message);
					setIsLoading(false);
				});
		} else {
			alert("Veuillez sélectionner un fichier PDF valide.");
			setIsLoading(false);
		}
	};

	const handleDeletePdf = () => {
		setIsLoading(true);
		axiosInstance
			.delete(`/student/cv/${user.id}`)
			.then(() => {
				toast.success("CV supprimé");
				setCv(null);
				setIsLoading(false);
			})
			.catch((error) => {
				toast.error("Erreur lors de la suppression du CV" + error.message);
				setIsLoading(false);
			});
	};

	return (
		<div className="container">
			{isLoading ? (
				<Loading />
			) : user.cvFile ? (
				<div>
					<h2>CV:</h2>
					<p>{user.cvFile.name}</p>
					<button className="btn btn-danger" onClick={handleDeletePdf}>
						Delete
					</button>
				</div>
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
			)}
		</div>
	);
}

export default Cv;

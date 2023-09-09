function Cv({cv, setCv}){
	const handlePdfUpload = (e) => {
		const file = e.target.files[0]
		if(file && file.type === 'application/pdf'){
			setCv(file)
		}else{
			alert('Veuillez sélectionner un fichier PDF valide.')
		}
	}

	const handleDeletePdf = () => {
		setCv(null)
	}

	return (
		<div>
			{cv && (
				<div>
					<h2>CV:</h2>
					<p>{cv.name}</p>
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

class CvFile{
	id
	fileName
	fileData
	cvState

	constructor(){
		this.reset()
	}

	init(cvFile){
		if(cvFile.id) this.id = cvFile.id
		else this.id = undefined
		if(cvFile.fileName) this.fileName = cvFile.fileName
		else this.fileName = undefined
		if(cvFile.fileData) this.fileData = cvFile.fileData
		else this.fileData = undefined
		if(cvFile.cvState) this.cvState = cvFile.cvState
		else this.cvState = undefined
	}

	reset(){
		this.id = undefined
		this.fileName = undefined
		this.fileData = undefined
		this.cvState = undefined
	}

}

export default CvFile;

class CvFile{
	id
	fileName
	fileData
	fileText
	cvState
	isApproved

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
		if(cvFile.isApproved !== undefined) this.isApproved = cvFile.isApproved;
		else this.isApproved = this.calculateApproval(cvFile)
	}

	reset(){
		this.id = undefined
		this.fileName = undefined
		this.fileData = undefined
		this.cvState = undefined
		this.isApproved = false
	}

	static readBytes(fileData) {
		const binaryString = atob(fileData);
		const length = binaryString.length;
		const uint8Array = new Uint8Array(length);

		for (let i = 0; i < length; i++) {
			uint8Array[i] = binaryString.charCodeAt(i);
		}

		return uint8Array;
	}

	calculateApproval(cvFile){
		return cvFile.cvState === "ACCEPTED";
	}

}

export default CvFile;

import React from "react";
import JsPDF from 'jspdf';
import CvFile from '../../model/CvFile'

const ShortCv = ({cv}) => {
    const OpenCv = () => {
        if (cv.fileData) {
            const pdf = new JsPDF();
            pdf.text(CvFile.readBytes(cv.fileData), 10, 10);
            const pdfBlob = pdf.output('blob');
            const pdfUrl = URL.createObjectURL(pdfBlob);
            const pdfIframe = document.createElement('iframe');
            pdfIframe.setAttribute('style', 'position:absolute;right:0; top:0; bottom:0; height:100%; width:100%; z-index:1000');
            pdfIframe.setAttribute('src', pdfUrl);
            pdfIframe.setAttribute('id', 'pdfIframe');
            document.body.appendChild(pdfIframe);
            const pdfCloseButton = document.createElement('button');
            pdfCloseButton.setAttribute('style', 'position:absolute;right:3em; top:3em; bottom:0; height: 3em; z-index:1001;');
            pdfCloseButton.setAttribute('class', 'btn btn-danger')
            pdfCloseButton.setAttribute('id', 'pdfCloseButton');
            pdfCloseButton.innerHTML = 'Close';
            const closePdfIframe = () => {
                document.body.removeChild(pdfIframe);
                document.body.removeChild(pdfCloseButton);
            }
            pdfCloseButton.addEventListener('click', closePdfIframe);
            document.body.appendChild(pdfCloseButton);
        }
    }

    return (
        <div className="row">
            <div className="col-12">
                <div className="row">
                    <div className="col-12">
                        <h4 className="text-dark fw-light my-5">Nom du fichier : <a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h4>
                        <p className="${cv.cvState === 'Accepted' ? 'border-success text-success':
                        cv.cvState === 'Pending' ? 'border-warning text-warning': 'border-danger text-danger'}`>">{cv.cvState}</p>

                    </div>
                </div>
            </div>
        </div>
    );
}
export default ShortCv;
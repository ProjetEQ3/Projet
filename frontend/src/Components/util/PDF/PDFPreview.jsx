import React from "react";
import PDFViewer from "./PDFViewer";

const PDFPreview = ({file, setIsDisplay}) => {
    const pdfBlob = new Blob([file], {type: 'application/pdf'});
    const closePdfIframe = () => {
        setIsDisplay(false);
    }

    return (
        <div className="row">
            <div className="col-12">
                <PDFViewer pdf={pdfBlob}/>
            </div>
        </div>
    );
}

export default PDFPreview;
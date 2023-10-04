import React from "react";

const PDFPreview = ({file, setIsDisplay}) => {
    const pdfBlob = new Blob([file], {type: 'application/pdf'});
    const pdfUrl = URL.createObjectURL(pdfBlob);
    const closePdfIframe = () => {
        setIsDisplay(false);
    }

    return (
        <div className="row">
            <div className="col-12">
                <embed src={pdfUrl} className="mx-auto w-100 rounded vh-100"/>
            </div>
        </div>
    );
}

export default PDFPreview;
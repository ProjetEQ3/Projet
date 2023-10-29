import React from "react";
import PDFViewer from "./PDFViewer";

const PDFPreview = ({file, contractComplete}) => {
    const pdfBlob = new Blob([file], {type: 'application/pdf'});

    return (
        <div className="row">
            <div className="col-12">
                <PDFViewer pdf={pdfBlob} contractComplete={contractComplete}/>
            </div>
        </div>
    );
}

export default PDFPreview;
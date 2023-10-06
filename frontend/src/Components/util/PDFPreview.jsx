import React from "react";
import { Document, Page } from 'react-pdf';

const PDFPreview = ({ file, setIsDisplay }) => {
    const pdfBlob = new Blob([file], { type: 'application/pdf' });
    const pdfUrl = URL.createObjectURL(pdfBlob);

    const closePdfIframe = () => {
        setIsDisplay(false);
    }

    return (
        <div className="row">
            <div className="col-12">
                <Document file={pdfUrl}>
                    <Page pageNumber={1} />
                </Document>
            </div>
        </div>
    );
}

export default PDFPreview;

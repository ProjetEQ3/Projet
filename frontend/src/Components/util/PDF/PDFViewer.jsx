import React, { useState, useEffect } from 'react';
import { Document, Page, pdfjs } from 'react-pdf';
import 'react-pdf/dist/esm/Page/TextLayer.css';
import 'react-pdf/dist/esm/Page/AnnotationLayer.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faSearchPlus,
    faSearchMinus,
    faDownload,
    faExternalLinkAlt
} from '@fortawesome/free-solid-svg-icons';
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../../context/DarkModeContext";

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url
).toString();

const PdfViewer = ({ pdf, contractComplete }) => {
    const [t] = useTranslation();
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const [scale, setScale] = useState(1); // To control zoom level

    const { darkMode } = useDarkMode();

    const onDocumentLoadSuccess = ({ numPages }) => {
        setNumPages(numPages);
        setPageNumber(1);
    };

    const handleError = (error) => {
        toast.error(t(error.response?.data.message))
    };

    const handleZoomIn = () => {
        if (scale > 2) return;
        setScale((prevScale) => prevScale + 0.1); // Increase scale for zooming in
    };

    const handleZoomOut = () => {
        if (scale <= 0.5) return;
        setScale((prevScale) => prevScale - 0.1);
    };

    const handleDownload = () => {
        const blobUrl = URL.createObjectURL(pdf);
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = 'file.pdf';
        link.click();
    };

    const handleOpenInNewTab = () => {
        const objectURL = URL.createObjectURL(pdf);
        window.newTab = window.open(objectURL, '_blank');

        // Revoke the object URL after opening the new tab
        URL.revokeObjectURL(objectURL);
    };

    useEffect(() => {
        // Your useEffect logic can go here if needed.
    }, []);

    return (
        <div className={'mx-auto'}>
            <div className={`text-center ${darkMode ? 'bg-light-dark' : 'bg-light'} m-0 pt-5`}>
                <div className="pdf-header btn-group m-0z">
                    <button className={'btn btn-outline-ose col-2'} onClick={handleZoomIn} disabled={scale === 2.5}>
                        <FontAwesomeIcon icon={faSearchPlus} />
                    </button>
                    <button className={'btn btn-outline-ose col-4 text-dark fw-bold'} disabled>
                        {`${(scale * 100).toFixed(0)}%`}
                    </button>

                    <button className={'btn btn-outline-ose col-2'} onClick={handleZoomOut} disabled={scale === 0.5}>
                        <FontAwesomeIcon icon={faSearchMinus} />
                    </button>
                    <button className={'btn btn-outline-ose col-2'} onClick={handleDownload} disabled={!contractComplete}>
                        <FontAwesomeIcon icon={faDownload} />
                    </button>
                    <button className={'btn btn-outline-ose col-2'} onClick={handleOpenInNewTab}>
                        <FontAwesomeIcon icon={faExternalLinkAlt} />
                    </button>
                </div>
            </div>
            <div className={`m-0 py-3 ${darkMode ? 'bg-light-dark' : 'bg-light'}`}></div>
            <div className={`pdf-document-container ${darkMode ? 'bg-light-dark' : 'bg-light'} py-3`}>
                <Document className={'overflow-scroll-x pdf-document-page'} file={pdf} onLoadSuccess={onDocumentLoadSuccess} onError={handleError}>
                    {Array.from({ length: numPages }, (_, i) => (
                        <>
                            <Page key={i + 1} className={'mx-auto'} pageNumber={i + 1} scale={scale} />
                            <div className="my-2"></div>
                        </>
                    ))}
                </Document>
            </div>
            <div className={`m-0 py-3 ${darkMode ? 'bg-light-dark' : 'bg-light'}`}></div>
            <div className="my-3"></div>
        </div>
    );
};

export default PdfViewer;

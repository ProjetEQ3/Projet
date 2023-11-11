import React, {forwardRef} from 'react'
import './FAQModal.css'

const FAQModal = forwardRef(({isOpen, onClose, children}, ref) => {
		if(!isOpen) return null

	const handleCloseClick = (e) => {
		e.stopPropagation()
		onClose()
	}

	return (
			<div className="modal-overlay">
				<div className="modal-container">
					<button className="close-button" onClick={handleCloseClick}>×</button>
					<div className="modal-content">
					{children}
					</div>
				</div>
			</div>
		)
	}
)

export default FAQModal

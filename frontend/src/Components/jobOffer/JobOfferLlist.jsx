import React from 'react'

function JobOfferList({jobOffers}){
	return (
		<div>
			<h1>Job Offers</h1>
			<ul>
				{jobOffers.map((jobOffer) => (
					<li key={jobOffer.id}>
						<h2>{jobOffer.PositionTitle}</h2>
						<p><strong>Department:</strong> {jobOffer.Department}</p>
						<p><strong>Location:</strong> {jobOffer.Location}</p>
						<p><strong>Description:</strong> {jobOffer.Description}</p>
						<p><strong>Salary:</strong> {jobOffer.Salary}</p>
						<p><strong>Formation:</strong> {jobOffer.Formation}</p>
						<p><strong>Due Date:</strong> {jobOffer.dueDate}</p>
						<p><strong>Expiration Date:</strong> {jobOffer.ExpirationDate}</p>
					</li>
				))}
			</ul>
		</div>
	)
}

export default JobOfferList;

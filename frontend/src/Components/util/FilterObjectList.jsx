import React, {useEffect, useState} from 'react'

const FilterObjectList = ({items, attributes, renderItem}) => {
	const [selectedAttribute, setSelectedAttribute] = useState()
	const [query, setQuery] = useState('')

	useEffect(() => {
		if(!items || !items.length) return
		if(!attributes || !attributes.length) return
		if(attributes.length > 0)
			setSelectedAttribute(attributes[0])
	}, [])

	const handleAttributeChange = (e) => {
		setSelectedAttribute(e.target.value)
	}

	const handleInputChange = (e) => {
		setQuery(e.target.value)
	}

	const filteredItems = items.filter(item =>
		String(item[selectedAttribute]).toLowerCase().includes(query.toLowerCase())
	)

	return (
		<div className="mb-3">
			<h5 className="mb-2">Filtrer par:</h5>
			<div className="d-flex align-items-center col-6">
				<select className="form-select me-2 flex-grow-1" value={selectedAttribute} onChange={handleAttributeChange}>
					{attributes.map(attr => (
						<option key={attr} value={attr}>{attr}</option>
					))}
				</select>
				<input
					className="form-control flex-grow-1"
					type="text"
					placeholder={`Filtrer par ${selectedAttribute}...`}
					value={query}
					onChange={handleInputChange}
				/>
			</div>
			<div className="mt-3">
				{renderItem(filteredItems)}
			</div>
		</div>
	)
}

FilterObjectList.defaultProps = {
	items: [],
	attributes: [],
	renderItem: () => {return (<>No items</>)}
}

export default FilterObjectList

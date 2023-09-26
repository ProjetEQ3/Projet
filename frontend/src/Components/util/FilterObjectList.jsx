import React, {useEffect, useState} from 'react'

const FilterObjectList = ({items, attributes, renderItem}) => {
	const [selectedAttribute, setSelectedAttribute] = useState()
	const [query, setQuery] = useState('')

	useEffect(() => {
		if(!items || !items.length)
			return
		if(!attributes || !attributes.length)
			return
		if(!selectedAttribute)
			return
		if(!attributes.includes(selectedAttribute) && attributes.length > 0)
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
		<div>
			<select value={selectedAttribute} onChange={handleAttributeChange}>
				{attributes.map(attr => (
					<option key={attr} value={attr}>{attr}</option>
				))}
			</select>

			<input
				type="text"
				placeholder={`Filter by ${selectedAttribute}...`}
				value={query}
				onChange={handleInputChange}
			/>

			{renderItem(filteredItems)}
		</div>
	)
}

FilterObjectList.defaultProps = {
	items: [],
	attributes: [],
	renderItem: () => null
}

export default FilterObjectList

import React, {useEffect, useState} from 'react'

const FilterObjectList = ({items, attributes, renderItem, selectOptions}) => {
	const [selectedAttribute, setSelectedAttribute] = useState('')
	const [currentPage, setCurrentPage] = useState(1)
	const [itemsPerPage, setItemsPerPage] = useState(10)
	const [query, setQuery] = useState('')
	const getAttributeKey = (attr) => attr.split(':')[0]
	const getAttributeDisplayName = (attr) => attr.split(':')[1] || getAttributeKey(attr)
	const isSelectAttribute = selectedAttribute && getAttributeKey(selectedAttribute).endsWith('.select')
	const actualAttribute = isSelectAttribute
		? getAttributeKey(selectedAttribute).replace('.select', '')
		: getAttributeKey(selectedAttribute)
	const filteredItems = items.filter(item => String(item[actualAttribute]).toLowerCase().includes(query.toLowerCase()))
	const totalPages = Math.ceil(filteredItems.length/itemsPerPage)
	const displayedItems = filteredItems.slice((currentPage - 1)*itemsPerPage, currentPage*itemsPerPage)

	useEffect(() => {
		if(!items || !items.length) return
		if(!attributes || !attributes.length) return
		if(attributes.length > 0)
			setSelectedAttribute(attributes[0])
	}, [])

	const goToNextPage = () => {
		if(currentPage < totalPages){
			setCurrentPage(prevPage => prevPage + 1)
		}
	}

	const goToPreviousPage = () => {
		if(currentPage > 1){
			setCurrentPage(prevPage => prevPage - 1)
		}
	}

	const handleItemsPerPageChange = (e) => {
		setItemsPerPage(Number(e.target.value))
		setCurrentPage(1)
	}

	const handleAttributeChange = (e) => {
		setSelectedAttribute(e.target.value)
		setQuery('')
	}

	const handleInputChange = (e) => {
		setQuery(e.target.value)
	}

	return (
		<div className="mb-3">
			<h5 className="mb-2">Filter by:</h5>

			<div className="d-flex align-items-center justify-content-between mb-2 col-12">
				<div className="d-flex align-items-center flex-grow-1 col-6">
					<select className="form-select me-2 flex-grow-1" value={selectedAttribute} onChange={handleAttributeChange}>
						{attributes.map(attr => (
							<option key={attr} value={attr}>{getAttributeDisplayName(attr)}</option>
						))}
					</select>

					{isSelectAttribute ? (
						<select className="form-control me-2 flex-grow-1" value={query} onChange={handleInputChange}>
							<option value="">Choose an option</option>
							{selectOptions[actualAttribute].map(option => (
								<option key={option} value={option}>{option}</option>
							))}
						</select>
					) : (
						<input
							className="form-control me-2 flex-grow-1"
							type="text"
							placeholder={`Filter by ${getAttributeDisplayName(selectedAttribute)}...`}
							value={query}
							onChange={handleInputChange}
						/>
					)}
				</div>

				{/*move to the right side*/}
				<div className="d-flex align-items-center col-6">
					<select className="form-select me-2" value={itemsPerPage} onChange={handleItemsPerPageChange}>
						{[10, 20, 50, 100].map(number => (
							<option key={number} value={number}>
								{number} per page
							</option>
						))}
					</select>

					<div className="pagination-controls d-flex align-items-center">
						<button className="btn btn-outline-secondary me-2" onClick={goToPreviousPage} disabled={currentPage === 1}>
							Previous
						</button>

						<div className="d-flex align-items-center me-2">
							<span className="me-1">Page</span>
							<span>{currentPage}</span>
							<span className="mx-1">of</span>
							<span>{totalPages}</span>
						</div>

						<button className="btn btn-outline-secondary" onClick={goToNextPage} disabled={currentPage === totalPages}>
							Next
						</button>
					</div>

				</div>
			</div>

			<div className="mt-3">
				{renderItem(displayedItems)}
			</div>
		</div>
	)


}

FilterObjectList.defaultProps = {
	items: [],
	attributes: [],
	renderItem: () => {
		return (<>No items</>)
	},
	selectOptions: {}
}

export default FilterObjectList

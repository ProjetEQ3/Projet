import React, {useEffect, useState} from 'react'
import {useTranslation} from "react-i18next";
import {t} from "i18next";

const FilterObjectList = ({items, attributes, renderItem, selectOptions}) => {
	const [t, i18n] = useTranslation()
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
	const filteredItems = Array.isArray(items)
		? items.filter(item => String(item[actualAttribute]).toLowerCase().includes(query.toLowerCase()))
		: []
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
			<div className="d-flex align-items-center justify-content-between mb-2 col-12">
				<div className="d-flex align-items-center col-6">
					<select className="form-select me-2 flex-grow-1" value={selectedAttribute} onChange={handleAttributeChange}>
						{attributes.map(attr => (
							<option key={attr} value={attr}>{getAttributeDisplayName(attr)}</option>
						))}
					</select>
					{isSelectAttribute ? (
						<select className="form-control me-2 flex-grow-1" value={query} onChange={handleInputChange}>
							<option value="">{t('choose')}</option>
							{selectOptions[actualAttribute].map(option => (
								<option key={option} value={option}>{t(option)}</option>
							))}
						</select>
					) : (
						<input
							className="form-control me-2 flex-grow-1"
							type="text"
							placeholder={t('filterBy') + getAttributeDisplayName(selectedAttribute)}
							value={query}
							onChange={handleInputChange}
						/>
					)}
				</div>
				<div className="d-flex align-items-center col-3">
					<select className="form-select me-2" value={itemsPerPage} onChange={handleItemsPerPageChange}>
						{[5, 10, 20, 50, 100].map(number => (
							<option key={number} value={number}>
								{number} {t('perPage')}
							</option>
						))}
					</select>
				</div>
			</div>
			<div className="my-3">
				{renderItem(displayedItems)}
			</div>
			<div className="pagination-controls mx-auto col-12">
				<div className="d-flex align-items-center justify-content-around">
					<button className="btn btn-outline-ose me-2 col-md-2 col-3" onClick={goToPreviousPage} disabled={currentPage === 1}>
						{t('previous')}
					</button>
					<div className="d-flex align-items-center me-2">
						<span className="me-1">Page</span>
						{
							totalPages > 0 ?
							<span>{currentPage}</span> :
							<span>0</span>
						}
						<span className="mx-1">{t('of')}</span>
						<span>{totalPages}</span>
					</div>
					<button className="btn btn-outline-ose col-md-2 col-3" onClick={goToNextPage} disabled={currentPage === totalPages || totalPages === 0}>
						{t('next')}
					</button>
				</div>
			</div>
		</div>
	)
}

FilterObjectList.defaultProps = {
	items: [],
	attributes: [],
	renderItem: () => {
		return (<>{t('noData')}</>)
	},
	selectOptions: {}
}

export default FilterObjectList

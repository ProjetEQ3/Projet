import React, {useState} from "react"
import {useNavigate} from "react-router-dom"
import Loading from "../util/Loading"
import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import {faPaperPlane, faArrowLeft} from "@fortawesome/free-solid-svg-icons"
import {useTranslation} from "react-i18next"
import {useDarkMode} from "../../context/DarkModeContext";

const NewOfferForm = ({user}) => {
	const [t] = useTranslation()
	const [estimateEndDate, setEstimateEndDate] = useState('')
	const navigate = useNavigate()
	const [isLoading, setIsLoading] = useState(false)
	const [formData, setFormData] = useState({
		title: '',
		department: '',
		location: '',
		nbOfCandidates: 1,
		description: '',
		salary: '',
		hoursPerWeek: '',
		startDate: '',
		duration: '',
		expirationDate: '',
		jobOfferState: 'SUBMITTED'
	})

	const [warnings, setWarnings] = useState({
		title: '',
		department: '',
		location: '',
		description: '',
		salary: '',
		hoursPerWeek: '',
		startDate: '',
		duration: '',
		expirationDate: '',
		nbOfCandidates: ''
	})

	const { darkMode } = useDarkMode();

	const saveOffer = async() => {
		setIsLoading(true)
		axiosInstance
			.post(`/employer/offer?employerId=${user.id}`, formData)
			.then((response) => {
				toast.success(t('createInternshipSuccess'))
				setIsLoading(false)
				navigate('/employer')
			})
			.catch((error) => {
					toast.error(t('createInternshipError'))
					setIsLoading(false)
				}
			)
	}

	const calculateEndDate = (startDate, duration) => {
		setEstimateEndDate(
			new Date(new Date(startDate).getTime() + duration*7*24*60*60*1000).toISOString().split('T')[0]
		)
	}

	const handleChanges = (e) => {
		const {name, value} = e.target
		if(name === 'duration' && formData.startDate !== '') calculateEndDate(formData.startDate, value)
		else if(name === 'startDate' && formData.duration !== '') calculateEndDate(value, formData.duration)
		setWarnings({...warnings, [name]: ""})
		setFormData({...formData, [name]: value.trim()})
	}

	const handleSubmit = (e) => {
		e.preventDefault()
		const validationErrors = {}
		if(formData.title === '') validationErrors.title = t('titleRequired')
		if(formData.department === '') validationErrors.department = t('departmentRequired')
		if(formData.location === '') validationErrors.location = t('locationRequired')
		if(formData.nbOfCandidates === '') validationErrors.nbOfCandidates = t('nbOfCandidatesRequired')
		else if(formData.nbOfCandidates < 1) validationErrors.nbOfCandidates = t('minimumNbOfCandidates')
		if(formData.description === '') validationErrors.description = t('descriptionRequired')
		if(formData.salary === '') validationErrors.salary = t('salaryRequired')
		else if(formData.salary <= 0) validationErrors.salary = t('salaryMustBePositive')
		if(formData.hoursPerWeek === '')validationErrors.hoursPerWeek = t('hoursPerWeekRequired')
		else if(formData.hoursPerWeek <= 0) validationErrors.hoursPerWeek = t('hoursPerWeekMustBePositive')
		if(formData.startDate === '') validationErrors.startDate = t('startDateRequired')
		else if(formData.startDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.startDate))
			validationErrors.startDate = t('startDateFormat')
		else if(formData.startDate && new Date(formData.startDate) < new Date())
			validationErrors.startDate = t('startDateNotPassed')
		if(formData.duration === '') validationErrors.duration = t('durationRequired')
		else if(formData.duration && formData.duration < 1) validationErrors.duration = t('minimumDuration')
		if(formData.expirationDate === '') validationErrors.expirationDate = t('endDateRequired')
		else if(formData.expirationDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.expirationDate))
			validationErrors.expirationDate = t('endDateFormat')
		else if(formData.expirationDate && new Date(formData.expirationDate) < new Date())
			validationErrors.expirationDate = t('endDateNotPassed')
		setWarnings(validationErrors)
		if(Object.keys(validationErrors).length === 0) saveOffer().then(r => r)
	}

	const handleBack = () => {
		navigate(-1)
	}

	return (
		<div className="container">
			<button className="btn btn-outline-ose" onClick={handleBack}>
				<FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
				{t('back')}
			</button>
			<h1 className="text-center fw-light">{t('newInternshipCreation')}</h1>
			{isLoading ? (
				<Loading/>
			) : (
				<div className="row">
					<div className="col-9 mx-auto">
						<form className="form-group" onSubmit={handleSubmit}>
							<div className="mb-3">
								<label htmlFor="title">{t('internshipTitle')}</label>
								<input
									type="text"
									className={`form-control ${warnings.title ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="title"
									placeholder={t('internshipTitle')}
									name="title"
									onChange={handleChanges}
									required
								/>
								{warnings.title && (<div className="invalid-feedback">{warnings.title}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="department">{t('department')}</label>
								<select
									className={`form-select ${warnings.department ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="department"
									name="department"
									defaultValue={t('chooseADepartment')}
									onChange={handleChanges}
									required
								>
									<option className="clickable" value="_410B0">{t('_410B0')}</option>
									<option className="clickable" value="_241A1">{t('_241A1')}</option>
									<option className="clickable" value="_420B0">{t('_420B0')}</option>
									<option className="clickable" value="_210AA">{t('_210AA')}</option>
									<option className="clickable" value="_144A1">{t('_144A1')}</option>
									<option className="clickable" value="_310A0">{t('_310A0')}</option>
									<option className="clickable" value="_145A0">{t('_145A0')}</option>
									<option className="clickable" value="_388A0">{t('_388A0')}</option>
									<option className="clickable" value="_140C0">{t('_140C0')}</option>
									<option className="clickable" value="_243C0">{t('_243C0')}</option>
									<option className="clickable" value="_243BA">{t('_243BA')}</option>
									<option className="clickable" value="_241D0">{t('_241D0')}</option>
									<option className="clickable" value="_243A0">{t('_243A0')}</option>
									<option value="_221B0">{t('_221B0')}</option>
									<option disabled={true}>{t('chooseADepartment')}</option>
								</select>
								{warnings.department && (<div className="invalid-feedback">{warnings.department}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="location">{t('location')}</label>
								<input
									type="text"
									className={`form-control ${warnings.location ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="location"
									placeholder={t('location')}
									name="location"
									onChange={handleChanges}
									required
								/>
								{warnings.location && (<div className="invalid-feedback">{warnings.location}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="nbOfCandidates">{t('nbOfCandidates')}</label>
								<input
									type="number"
									min="0"
									max="10"
									className={`form-control ${warnings.nbOfCandidates ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="nbOfCandidates"
									placeholder={t('nbOfCandidatesPlaceHolder')}
									name="nbOfCandidates"
									onChange={handleChanges}
									required
								/>
								{warnings.nbOfCandidates && (<div className="invalid-feedback">{warnings.nbOfCandidates}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="description">{t('internshipDescription')}</label>
								<textarea
									className={`form-control ${warnings.description ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="description"
									rows="3"
									placeholder={t('internshipDescription')}
									name="description"
									onChange={handleChanges}
									required
								></textarea>
								{warnings.description && (<div className="invalid-feedback">{warnings.description}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="salary">{t('salary')}</label>
								<input
									type="number"
									step="0.01"
									className={`form-control ${warnings.salary ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="salary"
									placeholder={t('salary')}
									name="salary"
									onChange={handleChanges}
									required
								/>
								{warnings.salary && (<div className="invalid-feedback">{warnings.salary}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="hoursPerWeek">{t('hoursPerWeek')}</label>
								<input
									type="number"
									step="0.01"
									className={`form-control ${warnings.hoursPerWeek ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="hoursPerWeek"
									placeholder={t('hoursPerWeek')}
									name="hoursPerWeek"
									onChange={handleChanges}
									required
								/>
								{warnings.hoursPerWeek && (<div className="invalid-feedback">{warnings.hoursPerWeek}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="startDate">{t('startDate')}</label>
								<input
									type="date"
									className={`form-control ${warnings.startDate ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="startDate"
									placeholder={t('startDate')}
									name="startDate"
									onChange={handleChanges}
									required
								/>
								{warnings.startDate && (<div className="invalid-feedback">{warnings.startDate}</div>)}
							</div>
							<div className="mb-3">
								<label htmlFor="duration">{t('duration')}</label>
								<input
									type="number"
									className={`form-control ${warnings.duration ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="duration"
									placeholder={t('duration')}
									name="duration"
									onChange={handleChanges}
									required
								/>
								{warnings.duration && (<div className="invalid-feedback">{warnings.duration}</div>)}
							</div>
							<p className="fst-italic fw-light text-dark">{t('estimateEndDate')} {estimateEndDate}</p>
							<div className="mb-3">
								<label htmlFor="expirationDate">{t('expirationDate')}</label>
								<input
									type="date"
									className={`form-control ${warnings.expirationDate ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
									id="expirationDate"
									placeholder={t('expirationDate')}
									name="expirationDate"
									onChange={handleChanges}
									required
								/>
								{warnings.expirationDate && (<div className="invalid-feedback">{warnings.expirationDate}</div>)}
							</div>
							<div className="row my-4">
								<div className="col-4 mx-auto">
									<button type="submit" className="btn btn-outline-ose col-12">
										{t('newInternshipSubmit')}{' '}
										<FontAwesomeIcon icon={faPaperPlane}/>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			)}
		</div>
	)
}

export default NewOfferForm

import {useTranslation} from "react-i18next";

const Home = ({jobOffers}) => {
    const {t} = useTranslation();


    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
            {
                jobOffers.length !== 0 && (
                    <>
                        <h2>{t('availableInternships')}</h2>
                        {
                            jobOffers.filter((jobOffer) => jobOffer.hasApplied !== true).map((offer, index) => (
                            <div key={index}>
                                <h5>{offer.title}</h5>
                            </div>))

                        }
                    </>
                )
            }
        </div>
    )
}

export default Home
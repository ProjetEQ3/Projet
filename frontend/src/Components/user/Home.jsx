
import {useTranslation} from "react-i18next";

const Home = () => {
    const {t} = useTranslation();


    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
        </div>
    )
}

export default Home
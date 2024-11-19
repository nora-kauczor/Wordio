import './Header.css'
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

type Props = {
    userId: string
    logout: () => void
    language: string
    setLanguage: React.Dispatch<React.SetStateAction<string>>;
}

export default function Header(props: Readonly<Props>) {
    // const [onReviewOrDisplayPage, setOnReviewOrDisplayPage] = useState(false)
    const navigate = useNavigate()
    // const location = useLocation();
    // useEffect(() => {
    //     if (location.pathname === "/review" || location.pathname === "/display"){
    //         setOnReviewOrDisplayPage(true)
    //     }
    // }, []);

    function handleChange(event: React.ChangeEvent<HTMLSelectElement>){
        props.setLanguage(event.target.value)
    }

    return (<div id={"header"}>
        <button
            onClick={() => navigate("/")}
            id={"app-name"}
            aria-live={"polite"}>
            Wordio
        </button>
        {props.userId && props.language &&
            // !onReviewOrDisplayPage &&
            (
            <div>
                <label htmlFor={"select-language"} className={"visually-hidden"}>
                    Select your language
                </label>
            <select id={"select-language"} value={props.language} onChange={handleChange}>
            <option value={"Spanish"}>🇪🇸 Spanish</option>
            <option value={"French"}>🇫🇷 French</option>
            <option value={"Italian"}>🇮🇹 Italian</option>
        </select>
            </div>
            )}
        {props.userId && <button id={"logout-button"}
                                   onClick={props.logout}
                                   aria-label={"Log out"}
                                   title={"Log out"}>
            logout</button>}
    </div>)
}
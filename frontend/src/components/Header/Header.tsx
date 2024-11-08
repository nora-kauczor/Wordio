import './Header.css'
import {useNavigate} from "react-router-dom";

type Props = {
    userId: string
    logout: () => void
    language: string
    setLanguage: React.Dispatch<React.SetStateAction<string>>;
}

export default function Header(props: Readonly<Props>) {
const navigate = useNavigate()
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
        {props.userId && props.language && (
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
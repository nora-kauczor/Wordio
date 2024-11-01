import './Header.css'

type Props = {
    userName: string
    logout: () => void
    language: string
    setLanguage: React.Dispatch<React.SetStateAction<string>>;
}

export default function Header(props: Readonly<Props>) {

    function handleChange(event: React.ChangeEvent<HTMLSelectElement>){
        props.setLanguage(event.target.value)
    }

    return (<div id={"header"}>
        <p id={"app-name"}>Wordio</p>
        {props.userName && props.language &&
            <select id={"select-language"} value={props.language} onChange={handleChange}>
            <option value={"Spanish"}>🇪🇸 Spanish</option>
            <option value={"French"}>🇫🇷 French</option>
            <option value={"Italian"}>🇮🇹 Italian</option>
        </select>}

        {props.userName && <button id={"logout-button"}
                                   onClick={props.logout}>logout</button>}
    </div>)
}
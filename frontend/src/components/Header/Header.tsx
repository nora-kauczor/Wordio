import './Header.css'
import React from "react";

type Props = {
    userName: string
    logout: () => void
    setLanguage: React.Dispatch<React.SetStateAction<string>>;
}

export default function Header(props: Readonly<Props>) {

    function selectLanguage(language: string): void {
        props.setLanguage(language)
    }

    return (<div id={"header"}>
        <p id={"app-name"}>Wordio</p>
        {props.userName &&<select id={"select-language"}>
            <option value="" disabled selected>Choose
                language
            </option>
            <option
                onClick={() => selectLanguage("Spanish")}
                onKeyDown={() => selectLanguage("Spanish")}>🇪🇸
                Spanish
            </option>
            <option   onClick={() => selectLanguage("French")}
                      onKeyDown={() => selectLanguage("French")}>🇫🇷 French</option>
            <option   onClick={() => selectLanguage("Italian")}
                      onKeyDown={() => selectLanguage("Italian")}>🇮🇹 Italian</option>
        </select>}

        {props.userName && <button id={"logout-button"}
                                   onClick={props.logout}>logout</button>}
    </div>)
}
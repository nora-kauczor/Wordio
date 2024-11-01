import './Header.css'
import React from "react";

type Props = {
    userName: string
    logout: () => void
    setLanguage: React.Dispatch<React.SetStateAction<string>>;
}

export default function Header(props: Readonly<Props>) {

    return (<div id={"header"}>
        <p id={"app-name"}>Wordio</p>
        {props.userName && props.setLanguage && <select id={"select-language"}>
            <option
                onClick={() => props.setLanguage("Spanish")}
                onKeyDown={() => props.setLanguage("Spanish")}>🇪🇸
                Spanish
            </option>
            <option   onClick={() => props.setLanguage("French")}
                      onKeyDown={() => props.setLanguage("French")}>🇫🇷 French</option>
            <option   onClick={() => props.setLanguage("Italian")}
                      onKeyDown={() => props.setLanguage("Italian")}>🇮🇹 Italian</option>
        </select>}

        {props.userName && <button id={"logout-button"}
                                   onClick={props.logout}>logout</button>}
    </div>)
}
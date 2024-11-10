import React from "react";
import './PickLanguagePopUp.css'

type Props = {
    setLanguage: React.Dispatch<React.SetStateAction<string>>
}
export default function PickLanguagePopUp(props: Readonly<Props>) {
    return (<div id={"language-pop-up"} className={"pop-up"}
                 role={"dialog"} aria-labelledby={"language-popup-header"}>
        <h2>Pick a language</h2>
        <article id={"language-pop-up-button-container"}>
            <button className={"language-pop-up-button"}
                    onClick={() => props.setLanguage("Spanish")}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key ===
                            ' ') props.setLanguage("Spanish");
                    }}
                    aria-label={"Select Spanish language"}
            ><p className={"language-icon"}>🇪🇸</p><p
                className={"language-name"}>Spanish</p>

            </button>
            <button className={"language-pop-up-button"}
                    onClick={() => props.setLanguage("French")}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key ===
                            ' ') props.setLanguage("French");
                    }}
                    aria-label={"Select French language"}
            >

                <p className={"language-icon"}>🇫🇷</p><p
                className={"language-name"}>French</p>
            </button>
            <button className={"language-pop-up-button"}
                    onClick={() => props.setLanguage("Italian")}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key ===
                            ' ') props.setLanguage("Italian");
                    }}
                    aria-label={"Select Italian language"}
            >
                <p className={"language-icon"}>🇮🇹</p><p
                    className={"language-name"}>Italian</p>
            </button>
        </article>
    </div>)
}
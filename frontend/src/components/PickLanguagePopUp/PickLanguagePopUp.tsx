import React from "react";
import './PickLanguagePopUp.css'
type Props = {
    setLanguage: React.Dispatch<React.SetStateAction<string>>
}
export default function PickLanguagePopUp(props: Readonly<Props>) {
    return (<div id={"language-pop-up"} className={"pop-up"}
                 role={"dialog"} aria-labelledby={"language-popup-header"}>
        <p>Pick a language to start learning</p>
        <article>
            <button
                onClick={() => props.setLanguage("Spanish")}
                onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') props.setLanguage("Spanish"); }}
                aria-label={"Select Spanish language"}
            >🇪🇸
                Spanish
            </button>
            <button onClick={() => props.setLanguage("French")}
                    onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') props.setLanguage("French"); }}
                    aria-label={"Select French language"}
            >🇫🇷 French
            </button>
            <button onClick={() => props.setLanguage("Italian")}
                    onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') props.setLanguage("Italian"); }}
                    aria-label={"Select Italian language"}
            >🇮🇹 Italian
            </button>
        </article>
    </div>)
}
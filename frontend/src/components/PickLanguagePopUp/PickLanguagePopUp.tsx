import React from "react";

type Props = {
    setLanguage: React.Dispatch<React.SetStateAction<string>>
}
export default function PickLanguagePopUp(props: Readonly<Props>) {
    return (<div id={"language-pop-up"}><p>Pick a language to start learning</p>
        <article>
            <button
                onClick={() => props.setLanguage("Spanish")}
                onKeyDown={() => props.setLanguage("Spanish")}>🇪🇸
                Spanish
            </button>
            <button onClick={() => props.setLanguage("French")}
                    onKeyDown={() => props.setLanguage("French")}>🇫🇷 French
            </button>
            <button onClick={() => props.setLanguage("Italian")}
                    onKeyDown={() => props.setLanguage("Italian")}>🇮🇹 Italian
            </button>
        </article>
    </div>)
}
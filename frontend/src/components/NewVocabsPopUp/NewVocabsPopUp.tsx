import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";
import React from "react";

type Props = {
    vocabs:Vocab[]
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    userId: string | undefined
}

export default function NewVocabsPopUp(props: Readonly<Props>) {
    const navigate = useNavigate()

    function handleClick() {
        props.setDisplayNewVocabsPopUp(false)
        props.setUseForm(true)
    }

    function getRandomVocab():Vocab {
        const inactiveVocabs:Vocab[] = props.vocabs
            .filter(vocab =>
                vocab.datesPerUser &&
                Object.keys(vocab.datesPerUser).length !== 0
                || !vocab.datesPerUser?.userId
            )
        const numberOfVocabs: number = inactiveVocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs)-1
        return props.vocabs[randomIndex];
    }

    function goToDisplayPageWithRandomVocab(){
        if (!getRandomVocab() || !getRandomVocab().id) {return}
        const id = getRandomVocab().id
        navigate(`/display/:${id}`)
    }


    return (<div id={"new-vocabs-popup"}
                 className={"pop-up"}>
        <button onClick={() => props.setDisplayNewVocabsPopUp(false)}
                className={"close-button"}
                aria-label={"Close new vocabulary pop-up"}>✕
        </button>
        <h2 className={"popup-header"}>Learn new vocabulary</h2>

        <div id={"button-wrapper"}>
            <button className={"new-vocabs-button"}
                    onClick={handleClick}
                    onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') handleClick(); }}
                    aria-label={"Enter new vocabulary"}
            >Enter new
                vocab
            </button>
            <button className={"new-vocabs-button"}
                    onClick={() => navigate("/backlog")}
                    onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') navigate("/backlog"); }}
                    aria-label={"Pick from backlog"}
            >Pick
                from backlog
            </button>
            <button className={"new-vocabs-button"}
                    onClick={goToDisplayPageWithRandomVocab}
                    onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') goToDisplayPageWithRandomVocab(); }}
                    aria-label={"Get random vocabulary"}
            >Get random
                vocab
            </button>
        </div>
    </div>)
}
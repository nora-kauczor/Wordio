import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";
import React from "react";

type Props = {
    vocabs:Vocab[]
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    userName: string | undefined
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
                || !vocab.datesPerUser?.userName
            )
        const numberOfVocabs: number = inactiveVocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs)-1
        return props.vocabs[randomIndex];
    }

    function goToDisplayPageWithRandomVocab(){
        if (!getRandomVocab() || !getRandomVocab()._id) {return}
        const _id = getRandomVocab()._id
        navigate(`/display/:${_id}`)
    }


    return (<div id={"new-vocabs-popup"}
                 className={"pop-up"}>
        <button onClick={() => props.setDisplayNewVocabsPopUp(false)}
                className={"close-button"}>✕
        </button>
        <h2 className={"popup-header"}>Learn new vocabulary</h2>

        <div id={"button-wrapper"}>
            <button className={"new-vocabs-button"}
                    onClick={handleClick}
                    onKeyDown={handleClick}>Enter new
                vocab
            </button>
            <button className={"new-vocabs-button"}
                    onClick={() => navigate("/backlog")}
                    onKeyDown={() => navigate("/backlog")}>Pick
                from backlog
            </button>
            <button className={"new-vocabs-button"}
                    onClick={goToDisplayPageWithRandomVocab}
                    onKeyDown={goToDisplayPageWithRandomVocab}>Get random
                vocab
            </button>
        </div>
    </div>)
}
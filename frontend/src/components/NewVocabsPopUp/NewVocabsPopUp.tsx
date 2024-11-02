import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";
import React from "react";

type Props = {
    vocabs:Vocab[]
    setShowPopUp: React.Dispatch<React.SetStateAction<boolean>>
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
}

export default function NewVocabsPopUp(props: Readonly<Props>) {

    function handleClick() {
        props.setShowPopUp(false)
        props.setUseForm(true)
    }

    function getRandomVocab():Vocab {
        const inactiveVocabs:Vocab[] = props.vocabs.filter(vocab => vocab.reviewDates && vocab.reviewDates?.length < 1)
        const numberOfVocabs: number = inactiveVocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs)-1
        return props.vocabs[randomIndex];
    }

    function goToDisplayPageWithRandomVocab(){
        if (!getRandomVocab() || !getRandomVocab()._id) {return}
        const _id = getRandomVocab()._id
        navigate(`/display/:${_id}`)
    }

    const navigate = useNavigate()
    return (<div id={"new-vocabs-popup"} className={"pop-up"}>
        <button onClick={() => props.setShowPopUp(false)}
                className={"close-button"}>✕
        </button>
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
                    onKeyDown={goToDisplayPageWithRandomVocab}>get random
                vocab
            </button>
        </div>
    </div>)
}
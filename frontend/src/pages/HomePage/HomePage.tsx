import './HomePage.css'
import NewVocabsPopUp from "../../components/NewVocabsPopUp/NewVocabsPopUp.tsx";
import {useNavigate} from "react-router-dom";
import React from "react";
import {Vocab} from "../../types/Vocab.ts";
import PickLanguagePopUp
    from "../../components/PickLanguagePopUp/PickLanguagePopUp.tsx";

type Props = {
    finishedReviewing: boolean
    allVocabsActivated: boolean
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    language?: string
    vocabs: Vocab[]
    setLanguage: React.Dispatch<React.SetStateAction<string>>
    userId: string
    displayNewVocabsPopUp: boolean
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
    activateVocab: (id: string) => void
}

export default function HomePage(props: Readonly<Props>) {
    const navigate = useNavigate()

    return (<div id={"homepage"} className={"page"} role={"main"}>
        <article id={"homepage-text-and-button"}>
            {props.finishedReviewing && props.language &&
                <p className={"homepage-text"}>You've got no vocabulary
                    scheduled
                    for today or you've already reviewed
                    all of it. <br/><br/>To start learning
                    (more) words click below. </p>}
            {!props.finishedReviewing && props.language &&
                <p className={"homepage-text"}>Nice to see you! <br/><br/>Click
                    below
                    to
                    get
                    started with your daily vocab
                    review.</p>}
            {props.finishedReviewing && props.language && <button
                className={"big-button"}
                onClick={() => props.setDisplayNewVocabsPopUp(true)}
                aria-label={"Open new vocabulary popup"}
            >
                New vocabulary</button>}
            {!props.finishedReviewing && props.language && <button
                className={"big-button"}
                onClick={() => navigate("/review")}

                aria-label={"Start vocabulary review"}
            >Review
            </button>}
        </article>
        {props.displayNewVocabsPopUp && props.vocabs &&
            <div className={"overlay"}/>}
        {props.displayNewVocabsPopUp && props &&
            <NewVocabsPopUp userId={props.userId}
                            vocabs={props.vocabs}
                            setUseForm={props.setUseForm}
                            setDisplayNewVocabsPopUp={props.setDisplayNewVocabsPopUp}
                            activateVocab={props.activateVocab}
            allVocabsActivated={props.allVocabsActivated}/>}
        {!props.language && <div className={"overlay"}/>}
        {!props.language &&
            <PickLanguagePopUp setLanguage={props.setLanguage}/>}
    </div>)
}
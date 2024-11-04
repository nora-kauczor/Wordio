import './HomePage.css'
import NewVocabsPopUp from "../../components/NewVocabsPopUp/NewVocabsPopUp.tsx";
import {useNavigate} from "react-router-dom";
import React, {useState} from "react";
import {Vocab} from "../../types/Vocab.ts";
import PickLanguagePopUp
    from "../../components/PickLanguagePopUp/PickLanguagePopUp.tsx";

type Props = {
    finishedReviewing: boolean
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    language?: string
    vocabs: Vocab[]
    setLanguage: React.Dispatch<React.SetStateAction<string>>
    userName: string
    displayNewVocabsPopUp: boolean
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
}

export default function HomePage(props: Readonly<Props>) {

    const navigate = useNavigate()

    return (<div id={"homepage"}>
        <article id={"homepage-text-and-button"}>
            {props.finishedReviewing && props.language &&
                <p className={"homepage-text"}>You've got no vocabulary
                    scheduled
                    for today or you've already reviewed
                    all of it. <br/><br/>To start learning
                    (more) words click below </p>}
            {!props.finishedReviewing && props.language &&
                <p className={"homepage-text"}>Nice to see you! <br/>Click below
                    to
                    get
                    started with your daily vocab
                    review</p>}
            {props.finishedReviewing && props.language && <button
                className={"homepage-button"}
                onClick={() => props.setDisplayNewVocabsPopUp(true)}
                onKeyDown={() => props.setDisplayNewVocabsPopUp(true)}>
                New vocabulary</button>}
            {!props.finishedReviewing && props.language && <button
                className={"homepage-button"}
                onClick={() => navigate("/review")}
                onKeyDown={() => navigate("/review")}>Review
            </button>}
        </article>
        {props.displayNewVocabsPopUp && props.vocabs && <div className={"overlay"}/>}
        {props.displayNewVocabsPopUp && props && <NewVocabsPopUp userName={props.userName}
                                               vocabs={props.vocabs}
                                               setUseForm={props.setUseForm}
                                               setDisplayNewVocabsPopUp={props.setDisplayNewVocabsPopUp}/>}
        {!props.language && <div className={"overlay"}/>}
        {!props.language &&
            <PickLanguagePopUp setLanguage={props.setLanguage}/>}

    </div>)
}
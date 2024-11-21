import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../../components/VocabList/VocabList.tsx";
import React from "react";
import {useNavigate} from "react-router-dom";


type Props = {
    vocabs: Vocab[]
    deleteVocab: (id: string) => void
    activateVocab: (id: string) => void
    language: string
    openForm: (id: string) => void
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
    userId: string
    allVocabsActivated: boolean
}

export default function BacklogPage(props: Readonly<Props>) {
    const navigate = useNavigate()
    if (props.vocabs.length < 1 && !props.allVocabsActivated) return <p
        className={"loading-message"}>Loading...</p>
    if (!Array.isArray(props.vocabs)) return <p
        className={"loading-message"}>Loading...</p>
    if(props.allVocabsActivated) return <p className={"loading-message"}>Backlog is empty</p>

    function handleClick(){
        navigate("/")
        props.setDisplayNewVocabsPopUp(false)
    }

    return (<div id={"backlog-page"} className={"page"} role={"main"}>
        <div style={{height: "40px"}}/>
        <VocabList vocabs={props.vocabs}
                   calendarMode={false}
                   activateVocab={props.activateVocab}
                   deleteVocab={props.deleteVocab}
                   openForm={props.openForm}
                   userId={props.userId}/>
        <button onClick={handleClick}
                aria-label={"Go back to the homepage"}
                className={"home-button"}>Home
        </button>
        <div style={{height: "10px"}}/>
    </div>)

}
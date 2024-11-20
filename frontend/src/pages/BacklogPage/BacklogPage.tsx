import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../../components/VocabList/VocabList.tsx";
import React from "react";


type Props = {
    vocabs: Vocab[]
    deleteVocab: (id: string) => void
    activateVocab: (id: string) => void
    language: string
    openForm: (id: string) => void
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    userId: string
    allVocabsActivated: boolean
}

export default function BacklogPage(props: Readonly<Props>) {

    if (props.vocabs.length < 1 && !props.allVocabsActivated) return <p
        className={"loading-message"}>Loading...</p>
    if (!Array.isArray(props.vocabs)) return <p
        className={"loading-message"}>Loading...</p>
    if(props.allVocabsActivated) return <p className={"loading-message"}>Backlog is empty</p>

    return (<div id={"backlog-page"} className={"page"} role={"main"}>
        <div style={{height: "50px"}}/>
        <VocabList vocabs={props.vocabs}
                    calendarMode={false}
                    activateVocab={props.activateVocab}
                    deleteVocab={props.deleteVocab}
                    openForm={props.openForm}
                    userId={props.userId}/>
        <div style={{height: "10px"}}/>
    </div>)

}
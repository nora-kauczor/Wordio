import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../../components/VocabList/VocabList.tsx";
import React, {useEffect} from "react";


type Props = {
    vocabs: Vocab[]
    deleteVocab: (id: string) => void
    activateVocab: (id: string) => void
    language: string
    openForm: (id: string) => void
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    userId: string
}

export default function BacklogPage(props: Readonly<Props>) {

    useEffect(() => {
        props.setUseForm(false)
    }, [props]);

    if (props.vocabs.length < 1 || !Array.isArray(props.vocabs)) return <p
        className={"loading-message"}>Loading...</p>

    return (<div id={"backlog-page"} role={"main"}>
        <VocabList vocabs={props.vocabs}
                   calendarMode={false}
                   activateVocab={props.activateVocab}
                   deleteVocab={props.deleteVocab}
                   openForm={props.openForm}
                   userId={props.userId}/>
    </div>)

}
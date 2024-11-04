import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList
    from "../../components/VocabList/VocabList.tsx";
import React, {useEffect} from "react";


type Props = {
    vocabs: Vocab[]
    deleteVocab: (_id: string) => void
    activateVocab: (_id: string) => void
    language:string
    openForm:(_id:string) => void
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
}

export default function BacklogPage(props: Readonly<Props>) {
    useEffect(() => {
        props.setUseForm(false)
    }, []);

    if(props.vocabs.length < 1 || !Array.isArray(props.vocabs)) return <p className={"loading-message"}>Loading...</p>
    return (<div id={"backlog-page"}>
        <VocabList vocabs={props.vocabs}
                   calendarMode={false}
                   activateVocab={props.activateVocab}
                   deleteVocab={props.deleteVocab}
        openForm={props.openForm}/>
    </div>)

}
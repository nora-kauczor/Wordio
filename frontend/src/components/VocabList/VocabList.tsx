import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import 'react-toastify/dist/ReactToastify.css';
import React, {useState} from "react";
import VocabListItem from "../VocabListItem/VocabListItem.tsx";

type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deactivateVocab?: (id: string) => void
    deleteVocab?: (id: string) => void
    activateVocab?: (id: string) => void
    openForm: (id: string) => void
    closeDayPopUp?: () => void
    userId: string
}

export default function VocabList(props: Readonly<Props>) {
    const [searchTerm, setSearchTerm] = useState("")
    const [filteredVocabs, setFilteredVocabs] = useState(props.vocabs)

    function handleChangeInput(event: React.ChangeEvent<HTMLInputElement>) {
        const input: string = event.target.value;
        setSearchTerm(input)
        if (!input) {
            setFilteredVocabs(props.vocabs);
            return
        }
        const inputLength: number = input.length
        const matchingVocabs: Vocab[] = props.vocabs.filter(vocab => {
            const indexOfSpace: number = vocab.word.indexOf(" ")
            if (vocab.word.substring(0, inputLength).toLowerCase() ===
                input.toLowerCase() || vocab.word.substring(indexOfSpace + 1,
                    indexOfSpace + 1 + inputLength)
                    .toLowerCase() === input.toLowerCase()) {
                return true
            }

        })
        setFilteredVocabs(matchingVocabs)
    }

    function reset() {
        setSearchTerm("")
        setFilteredVocabs(props.vocabs)
    }


    return (<div id={"vocab-list"}>
        <div id={"input-and-button-wrapper"}>
            <label htmlFor={"search-field"}
                   className={"visually-hidden"}>Type here to search
                vocabs</label>
            <input id={"search-field"}
                   value={searchTerm}
                   onChange={handleChangeInput}/>
            <button id={"reset-button"}
                    onClick={reset}
                    onKeyDown={reset}
            >Reset
            </button>
        </div>
        <ul id={"list"} role={"list"}
            className={`${props.calendarMode ? "list-calendar-mode" :
                "list-backlog-mode"}`}>
            {filteredVocabs.map(vocab => <VocabListItem
                key={vocab.id}
                vocab={vocab}
                calendarMode={props.calendarMode}
                openForm={props.openForm}
                userId={props.userId}
                deactivateVocab={props.deactivateVocab}
                activateVocab={props.activateVocab}
                deleteVocab={props.deleteVocab}
                closeDayPopUp={props.closeDayPopUp}/>)}
        </ul>
    </div>)

}


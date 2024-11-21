import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import 'react-toastify/dist/ReactToastify.css';
import React, {useEffect, useState} from "react";
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
    useEffect(() => {
        if (searchTerm) {
            filterVocabs(searchTerm)
        } else {setFilteredVocabs(props.vocabs)}
    }, [props.vocabs]);

    function handleChangeInput(event: React.ChangeEvent<HTMLInputElement>) {
        const input: string = event.target.value;
        setSearchTerm(input)
        if (!input) {
            setFilteredVocabs(props.vocabs);
            return
        }
        filterVocabs(input)
    }

    function filterVocabs(input: string): void {
        const matchingVocabs = []
        for (let i: number = 0; i < props.vocabs.length; i++) {
            const translation: string = props.vocabs[i].translation
            if (doesAnySubstringMatch(translation, input)) {
                matchingVocabs.push(props.vocabs[i])
            }
        }
        setFilteredVocabs(matchingVocabs)
    }

    function doesAnySubstringMatch(translation: string,
                                   input: string): boolean {
        const firstIndeces: number[] = [];
        for (let z: number = 0; z < translation.length; z++) {
            if (translation.charAt(z) === input.charAt(0)) {
                firstIndeces.push(z);
            }
        }
        if (firstIndeces.length < 1) {
            return false
        }
        const filteredIndeces: number[] = firstIndeces.filter(
            index => translation.substring(index, index + input.length) ===
                input)
        return filteredIndeces.length >= 1;
    }

    function reset() {
        setSearchTerm("")
        setFilteredVocabs(props.vocabs)
    }

    return (<div id={"vocab-list"}>

        <div id={"search-section"}>
            <label htmlFor={"search-field"}>Search vocabulary by
                translation</label>
            <div id={"search-section-input-and-button"}>
                <input id={"input"}
                       value={searchTerm}
                       onChange={handleChangeInput}/>
                <button id={"reset-button"}
                        onClick={reset}
                >Reset
                </button>
            </div>
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


import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import {useNavigate} from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css';
import React, {useState} from "react";

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
    const navigate = useNavigate()

    function handleClickActivate(id: string | null): void {
        if (!id || !props.activateVocab) {
            return
        }
        props.activateVocab(id)
        navigate(`/display/:${id}`)
    }

    function handleClickDeactivate(id: string | null): void {
        if (!id || !props.deactivateVocab) {
            return
        }
        props.deactivateVocab(id)
    }

    function handleClickEdit(id: string | null) {
        if (!id) {
            return
        }
        if (props.closeDayPopUp) {
            props.closeDayPopUp()
        }
        props.openForm(id)
    }

    function handleClickDelete(id: string | null) {
        if (!id || !props.deleteVocab) {
            return
        }
        props.deleteVocab(id)
    }

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
                input.toLowerCase() || vocab.word.substring(indexOfSpace+1, indexOfSpace +1+ inputLength)
                    .toLowerCase() === input.toLowerCase()) {
                return true
            }

        })
        setFilteredVocabs(matchingVocabs)
    }


    return (<div id={"vocab-list"}>

        <div id={"input-and-button-wrapper"}>
            <label htmlFor={"search-field"}
                   className={"visually-hidden"}>Type here to search
                vocabs</label>
            <input id={"search-field"}
                // defaultValue={"Search vocabs"}
                   value={searchTerm}
                   onChange={handleChangeInput}/>
            <button id={"reset-button"}
                    onClick={() => setSearchTerm("")}>Reset
            </button>
        </div>
        <ul id={"list"} role={"list"}>
            {filteredVocabs.map(vocab => <li key={vocab.id}
                                             className={`list-item card ${props.calendarMode ?
                                                 "list-item-calendar-mode" :
                                                 "list-item-backlog-mode"}`}>
                <div id={"text-wrapper"}>
                    <p id={"vocab-word"}>{vocab.word}</p>
                    <article id={"translation-and-info-wrapper"}>
                        <p id={"vocab-translation"}>{vocab.translation}</p>
                        <p id={"vocab-info"}>{vocab.info}</p>
                    </article>
                </div>
                <div id={"vocab-list-button-wrapper"}>
                    {vocab.createdBy === props.userId && vocab.id &&
                        <button className={"vocab-list-button"}
                                onClick={() => handleClickEdit(vocab.id)}
                                onKeyDown={(e) => {
                                    if (e.key === 'Enter' || e.key ===
                                        ' ') handleClickEdit(vocab.id);
                                }}
                                aria-label={`Edit ${vocab.word}`}
                        >edit</button>}

                    <button
                        className={vocab.createdBy === props.userId ?
                            "vocab-list-button" :
                            "de_activate-button-non-editable-vocab"}
                        onClick={() => vocab.id && (props.calendarMode ?
                            handleClickDeactivate(vocab.id) :
                            handleClickActivate(vocab.id))}
                        onKeyDown={(e) => {
                            if (vocab.id &&
                                (e.key === 'Enter' || e.key === ' ')) {
                                if (props.calendarMode) {
                                    handleClickDeactivate(vocab.id);
                                } else {
                                    handleClickActivate(vocab.id);
                                }
                            }
                        }}
                        aria-label={props.calendarMode ?
                            `Deactivate ${vocab.word}` :
                            `Activate ${vocab.word}`}>
                        {props.calendarMode ? "deactivate" :
                            "activate"}</button>


                    {vocab.createdBy === props.userId && vocab.id && <button
                        className={"vocab-list-button"}
                        onClick={() => handleClickDelete(vocab.id)}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter' || e.key ===
                                ' ') handleClickDelete(vocab.id);
                        }}
                        aria-label={`Delete ${vocab.word}`}
                    >
                        delete
                    </button>}
                </div>
            </li>)}
        </ul>
    </div>)

}


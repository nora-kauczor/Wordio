import './Form.css'
import {Vocab} from "../../types/Vocab.ts";
import React, {useEffect, useState} from "react";

type Props = {
    language: string
    vocabToEdit: Vocab | undefined
    createVocab: (vocab: Vocab) => void
    createAndActivateVocab: (vocab: Vocab) => void
    editVocab: (vocab: Vocab) => void
    userName: string
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
}

export default function Form(props: Readonly<Props>) {

    const [wordInput, setWordInput] = useState<string>("")
    const [translationInput, setTranslationInput] = useState<string>("")
    const [infoInput, setInfoInput] = useState<string>("")
    useEffect(() => {
        if (!props.vocabToEdit) {
            return
        }
        setWordInput(props.vocabToEdit.word)
        setTranslationInput(props.vocabToEdit.translation)
        setInfoInput(props.vocabToEdit.info)
    }, []);

    function handleClick(clickedButton: string) {
        if (props.vocabToEdit) {
            const editedVocab: Vocab = {
                _id: props.vocabToEdit._id,
                word: wordInput,
                translation: translationInput,
                info: infoInput,
                language: props.language,
                datesPerUser: props.vocabToEdit.datesPerUser,
                createdBy: props.userName
            }
            props.editVocab(editedVocab)
        } else {
            const newVocab: Vocab = {
                _id: null,
                word: wordInput,
                translation: translationInput,
                info: infoInput,
                language: props.language,
                createdBy: props.userName
            }
            if (clickedButton === "create") {
                props.createVocab(newVocab)
            } else {
                props.createAndActivateVocab(newVocab)
            }
        }
    }

    function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
        const source = event.target.name
        const data = new InputEvent(event.target.value)
        const value = data.type
        if (source === "word") {
            setWordInput(value);
            return
        }
        if (source === "translation") {
            setTranslationInput(value);
            return
        }
        setInfoInput(value)
    }

    return (<div id={"form"} className={"pop-up"}>
        <button className={"close-button"}
                onClick={() => props.setUseForm(false)}
                >✕
        </button>
        <div id={"input-and-label-wrapper"}>
            <label htmlFor={"word-input"}>Your Vocab</label>
            <input name={"word"} id={"word-input"} value={wordInput}
                   onChange={handleChange}/>
            <label htmlFor={"translation-input"}>Translation into
                English</label>
            <input name={"translation"} id={"translation-input"}
                   value={translationInput} onChange={handleChange}/>
            <label htmlFor={"info-input"}>Additional info, e.g.
                "colloquial"</label>
            <input name={"info"} id={"info-input"} value={infoInput}
                   onChange={handleChange}/>
        </div>
            <button name={"create"}
                    onClick={() => handleClick("create")}
            >create
            </button>
            <button name={"create-and-activate"}
                    onClick={() => handleClick("create-and-activate")}
            >create and activate
            </button>
        </div>
        )
        }
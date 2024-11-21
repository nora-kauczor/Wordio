import './Form.css'
import {Vocab} from "../../types/Vocab.ts";
import React, {useEffect, useState} from "react";

type Props = {
    language: string
    vocabToEdit: Vocab | undefined
    createVocab: (vocab: Vocab) => void
    createAndActivateVocab: (vocab: Vocab) => void
    editVocab: (vocab: Vocab) => void
    editAndActivateVocab: (vocab: Vocab) => void
    userId: string
    closeForm: () => void
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
        const vocab: Vocab = {
            id: props.vocabToEdit ? props.vocabToEdit.id : null,
            word: wordInput,
            translation: translationInput,
            info: infoInput,
            language: props.language,
        };
        if (props.vocabToEdit) {
            if (clickedButton === "submit") {
                props.editVocab(vocab)
            } else {
                props.editAndActivateVocab(vocab)
            }
        } else {
            if (clickedButton === "submit") {
                props.createVocab(vocab)
            } else {
                props.createAndActivateVocab(vocab)
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

    const vocabIsAlreadyActive: undefined | boolean = props.vocabToEdit &&
        props.vocabToEdit?.datesPerUser?.[props.userId] &&
        props.vocabToEdit?.datesPerUser[props.userId].length > 0

    return (<div id={"form"} className={"pop-up"}
                 role={"dialog"} aria-labelledby={"form-title"}
                 aria-modal={"true"}>
        <button className={"close-button"}
                onClick={props.closeForm}
                aria-label={"Close form"}
        >✕
        </button>
        <h2 className={"popup-header"} id={"form-header"}>{props.vocabToEdit ? 'Edit your vocab' :
            'Create your vocab'}</h2>
        <div id={"input-and-label-wrapper"}>
            <label htmlFor={"word-input"}>Your Vocab</label>
            <input name={"word"} id={"word-input"} value={wordInput}
                   onChange={handleChange}
                   type={"text"}
                   maxLength={20}
                   aria-required={"true"}
                   required
            />
            <label htmlFor={"translation-input"}>Translation into
                English</label>
            <input name={"translation"} id={"translation-input"}
                   value={translationInput} onChange={handleChange}
                   type={"text"}
                   maxLength={20}
                   aria-required={"true"}
                   required/>
            <label htmlFor={"info-input"}>Additional info, e.g.
                "colloquial"</label>
            <input name={"info"} id={"info-input"} value={infoInput}
                   type={"text"} maxLength={20}
                   onChange={handleChange}/>
        </div>
        <button className={"form-button"}
                onClick={() => handleClick("submit")}>submit
        </button>
        {!vocabIsAlreadyActive && <button className={"form-button"}
                                          onClick={() => handleClick(
                                              "submit-and-activate")}
        >submit and activate
        </button>}
    </div>)
}
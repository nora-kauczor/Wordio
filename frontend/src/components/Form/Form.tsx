import './Form.css'
import {Vocab} from "../../types/Vocab.ts";
import React, {useEffect, useState} from "react";

type Props = {
    editMode: boolean
    language: string
    oldVocab?: Vocab
    createVocab: (vocab: Vocab) => void
    editVocab: (vocab: Vocab) => void
}

export default function Form(props: Readonly<Props>) {

    const [wordInput, setWordInput] = useState<string>("")
    const [translationInput, setTranslationInput] = useState<string>("")
    const [infoInput, setInfoInput] = useState<string>("")
    useEffect(() => {
        if (!props.oldVocab) {
            return
        }
        setWordInput(props.oldVocab.word)
        setTranslationInput(props.oldVocab.translation)
        setInfoInput(props.oldVocab.info)
    }, []);

    function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (props.editMode) {
            const editedVocab: Vocab = {
                _id: props.oldVocab._id,
                word: wordInput,
                translation: translationInput,
                info: infoInput,
                language: props.language,
                reviewDates: props.oldVocab.reviewDates,
                editable: true
            }
            props.editVocab(editedVocab)
        } else {
            const newVocab: Vocab = {
                _id: null,
                word: wordInput,
                translation: translationInput,
                info: infoInput,
                language: props.language,
                editable: true
            }
            props.createVocab(newVocab)
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

    return (<form id={"form"} onSubmit={handleSubmit}>
        <label htmlFor={"word-input"}>Word</label>
        <input name={"word"} id={"word-input"} value={wordInput}
               onChange={handleChange}/>
        <label htmlFor={"translation-input"}>Translation</label>
        <input name={"translation"} id={"translation-input"}
               value={translationInput} onChange={handleChange}/>
        <label htmlFor={"info-input"}>Additional info, e.g.
            "colloquial"</label>
        <input name={"info"} id={"info-input"} value={infoInput}
               onChange={handleChange}/>
        <button>Submit</button>
    </form>)
}
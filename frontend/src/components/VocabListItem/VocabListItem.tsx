import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";
import './VocabListItem.css'

type Props = {
    vocab: Vocab
    calendarMode: boolean
    deactivateVocab?: (id: string) => void
    deleteVocab?: (id: string) => void
    activateVocab?: (id: string) => void
    openForm: (id: string) => void
    closeDayPopUp?: () => void
    userId: string
}


export default function VocabListItem(props: Readonly<Props>) {
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

    return (<li
            id={"list-item"}
            className={`card ${props.calendarMode ? "list-item-calendar-mode" :
                "list-item-backlog-mode"}`}>

            <div id={"text-wrapper"}
                 className={` ${props.calendarMode ?
                     "text-wrapper-calendar-mode" :
                     "text-wrapper-backlog-mode"}`}>

                <p id={"vocab-word"}>{props.vocab.word}</p>

                <article id={"translation-and-info-wrapper"}>
                    <p id={"vocab-translation"}>{props.vocab.translation}</p>
                    <p id={"vocab-info"}>{props.vocab.info}</p>
                </article>

            </div>

            <div id={"list-item-button-wrapper"}>
                {props.vocab.createdBy === props.userId && props.vocab.id &&
                    <button className={"list-item-button"}
                            onClick={() => handleClickEdit(props.vocab.id)}
                            aria-label={`Edit ${props.vocab.word}`}
                    >edit</button>}
                <button
                    className={props.vocab.createdBy === props.userId ?
                        "vocab-list-button" :
                        "de_activate-button-non-editable-vocab"}
                    onClick={() => props.vocab.id && (props.calendarMode ?
                        handleClickDeactivate(props.vocab.id) :
                        handleClickActivate(props.vocab.id))}
                    aria-label={props.calendarMode ?
                        `Deactivate ${props.vocab.word}` :
                        `Activate ${props.vocab.word}`}>
                    {props.calendarMode ? "deactivate" : "activate"}</button>


                {props.vocab.createdBy === props.userId && props.vocab.id &&
                    <button
                        className={"vocab-list-button"}
                        onClick={() => handleClickDelete(props.vocab.id)}
                        aria-label={`Delete ${props.vocab.word}`}>
                        delete
                    </button>}
            </div>
        </li>


    )
}
import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import {useNavigate} from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css';

type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deactivateVocab?: (id: string) => void
    deleteVocab?: (id: string) => void
    activateVocab?: (id: string) => void
    openForm: (id: string) => void
    closeDayPopUp?: () => void
    userId:string
}

export default function VocabList(props: Readonly<Props>) {
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


    return (<ul id={"vocab-list"} role={"list"}>
        {props.vocabs.map(vocab => <li key={vocab.id}
                                       className={"list-item + card"}>
            <article id={"text-wrapper"}>
                <p>{vocab.word}</p>
                <div id={"translation-and-info-wrapper"}>
                <p>{vocab.translation}</p>
                <p id={"vocab-info"}>{vocab.info}</p>
                </div>
            </article>
            <div id={"list-item-button-wrapper"}>
                {vocab.createdBy  === props.userId  && vocab.id ? <button
                    onClick={() => handleClickEdit(vocab.id)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key === ' ') handleClickEdit(
                            vocab.id);
                    }}
                    aria-label={`Edit ${vocab.word}`}
                >edit</button> : null}
                <button
                    onClick={() => vocab.id &&
                        (props.calendarMode ? handleClickDeactivate(vocab.id) :
                            handleClickActivate(vocab.id))}
                    onKeyDown={(e) => {
                        if (vocab.id && (e.key === 'Enter' || e.key === ' ')) {
                            if (props.calendarMode) {
                                handleClickDeactivate(vocab.id);
                            } else {
                                handleClickActivate(vocab.id);
                            }
                        }
                    }}
                    aria-label={props.calendarMode ?
                        `Deactivate ${vocab.word}` : `Activate ${vocab.word}`}>
                    {props.calendarMode ? "deactivate" : "activate"}</button>
                {vocab.createdBy === props.userId && vocab.id ?
                    <button
                    onClick={() => handleClickDelete(vocab.id)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key ===
                            ' ') handleClickDelete(vocab.id);
                    }}
                    aria-label={`Delete ${vocab.word}`}
                >
                        delete
                    </button>
                    :
                    <button/>
                }
            </div>
        </li>)}
    </ul>)

}


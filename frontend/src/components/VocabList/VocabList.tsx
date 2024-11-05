import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import {useNavigate} from "react-router-dom";

type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deactivateVocab?: (_id: string) => void
    deleteVocab?: (_id: string) => void
    activateVocab?: (_id: string) => void
    openForm: (_id: string) => void
    closeDayPopUp?: () => void
    userName:string
}

export default function VocabList(props: Readonly<Props>) {
    const navigate = useNavigate()

    function handleClickActivate(_id: string | null): void {
        if (!_id || !props.activateVocab) {
            return
        }
        props.activateVocab(_id)
        navigate(`/display/:${_id}`)
    }

    function handleClickDeactivate(_id: string | null): void {
        if (!_id || !props.deactivateVocab) {
            return
        }
        props.deactivateVocab(_id)
    }

    function handleClickEdit(_id: string | null) {
        if (!_id) {
            return
        }
        if (props.closeDayPopUp) {
            props.closeDayPopUp()
        }
        props.openForm(_id)
    }

    function handleClickDelete(_id: string | null) {
        if (!_id || !props.deleteVocab) {
            return
        }
        props.deleteVocab(_id)
    }

    return (<ul id={"vocab-list"} role={"list"}>
        {props.vocabs.map(vocab => <li key={vocab._id}
                                       className={"list-item"}>
            <div className={"list-item-word-wrapper"}>
                <p>{vocab.word}</p>
                <p>{vocab.translation}</p>
            </div>
            <div className={"list-item-button-wrapper"}>
                {vocab.createdBy  === props.userName  && vocab._id ? <button
                    onClick={() => handleClickEdit(vocab._id)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key === ' ') handleClickEdit(
                            vocab._id);
                    }}
                    aria-label={`Edit ${vocab.word}`}
                >edit</button> : null}
                <button
                    onClick={() => vocab._id &&
                        (props.calendarMode ? handleClickDeactivate(vocab._id) :
                            handleClickActivate(vocab._id))}
                    onKeyDown={(e) => {
                        if (vocab._id && (e.key === 'Enter' || e.key === ' ')) {
                            if (props.calendarMode) {
                                handleClickDeactivate(vocab._id);
                            } else {
                                handleClickActivate(vocab._id);
                            }
                        }
                    }}
                    aria-label={props.calendarMode ?
                        `Deactivate ${vocab.word}` : `Activate ${vocab.word}`}>
                    {props.calendarMode ? "deactivate" : "activate"}</button>
                {vocab.createdBy === props.userName && vocab._id ? <button
                    onClick={() => handleClickDelete(vocab._id)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key ===
                            ' ') handleClickDelete(vocab._id);
                    }}
                    aria-label={`Delete ${vocab.word}`}
                >delete</button> : <button/>}
            </div>
        </li>)}
    </ul>)

}


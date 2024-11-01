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

    return (<ul id={"vocab-list"}>
        {props.vocabs.map(vocab => <li key={vocab._id}
                                       className={"list-item"}
        >
            <div className={"list-item-word-wrapper"}>
                <p>{vocab.word}</p>
                <p>{vocab.translation}</p>
            </div>
            <div className={"list-item-button-wrapper"}>
                {vocab.createdBy && vocab._id ? <button
                    onClick={() => handleClickEdit(vocab._id)}
                    onKeyDown={() => handleClickEdit(vocab._id)}
                >edit</button> : <button/>}
                <button
                    onClick={() => vocab._id &&
                        (props.calendarMode ? handleClickDeactivate(vocab._id) :
                            handleClickActivate(vocab._id))}
                    onKeyDown={() => vocab._id &&
                        (props.calendarMode ? handleClickDeactivate(vocab._id) :
                            handleClickActivate(vocab._id))}>
                    {props.calendarMode ? "deactivate" : "activate"}</button>
                {vocab.createdBy && vocab._id ? <button
                    onClick={() => handleClickDelete(vocab._id)}
                    onKeyDown={() => handleClickDelete(vocab._id)}
                >delete</button> : <button/>}
            </div>
        </li>)}
    </ul>)

}


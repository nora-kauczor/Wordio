import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
import {useNavigate} from "react-router-dom";

type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deactivateVocab?: (_id: string) => void
    deleteVocab?: (_id: string) => void
    activateVocab?: (_id: string) => void
    setDisplayedVocab: React.Dispatch<React.SetStateAction<Vocab>>;
}

export default function VocabList(props: Readonly<Props>) {
    const navigate = useNavigate()

    function handleClick(_id: string | null): void {
        if (!_id) {
            return
        }
        if (props.calendarMode) {
            if (!props.deactivateVocab) {
                return
            }
            props.deactivateVocab(_id)
        } else {
            if (!props.activateVocab) {
                return
            }
            props.activateVocab(_id)
            navigate(`/display/:${_id}`)
        }
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
                <button
                    onClick={() => vocab._id && handleClick(vocab._id)}
                    onKeyDown={() => vocab._id && handleClick(vocab._id)}>
                    {props.calendarMode ? "deactivate" : "activate"}</button>
            </div>
        </li>)}
    </ul>)

}


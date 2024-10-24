import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
type Props = {
    vocabs: Vocab[]
    calendarMode: boolean

}

export default function VocabList(props: Readonly<Props>) {

    return (<ul id={"vocab-list"}>
        {props.vocabs.map(vocab => <li key={vocab._id}
                                       className={"list-item"}
        >
            <div className={"list-item-word-wrapper"}>
            <p>{vocab.word}</p>
            <p>{vocab.translation}</p>
            </div>
            <div className={"list-item-button-wrapper"}>
                <button>{props.calendarMode ? "remove" : "other"}</button>
                <button>{props.calendarMode ? "shift" : "other"}</button>
            </div>
        </li>)}
    </ul>)

}


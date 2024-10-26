import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deactivateVocab:(_id:string)=> void
}

export default function VocabList(props: Readonly<Props>) {

    function handleClick(_id:string):void{
        if (props.calendarMode){
            props.deactivateVocab(_id)
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
                <button onClick={()=> vocab._id && handleClick(vocab._id)}>{props.calendarMode ? "deactivate" : "activate"}</button>
                <button>{props.calendarMode ? "shift" : "other"}</button>
            </div>
        </li>)}
    </ul>)

}


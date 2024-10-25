import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'

type Props = {
    vocabs: Vocab[]
    calendarMode: boolean
    deleteVocab?: (_id: string) => void
    activateVocab?: (_id: string) => void
}

export default function VocabList(props: Readonly<Props>) {

    if (!props.vocabs){return <p>Loading...</p>}
    function handleClickActivate(_id: string | null): void {
        if (!_id || !props.activateVocab){return}
        props.activateVocab(_id)
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
            <button onClick={() =>  handleClickActivate(vocab._id)} onKeyDown={() => handleClickActivate(vocab._id)}>{props.calendarMode ? "shift" : "activate"}</button>
            <button
                >{!props.calendarMode ? "remove" : "delete"}</button>
        </div>
    </li>)}
</ul>)

}


import {Vocab} from "../../types/Vocab.ts";
import './VocabList.css'
type Props = {
    vocabs: Vocab[]

}

export default function VocabList(props: Readonly<Props>) {

    return (<ul id={"vocab-list"}>
        {props.vocabs.map(vocab => <li key={vocab._id}
                                       className={"list-item"}
            // onClick={handleClick}
        >
            <p>{vocab.word}</p>
            <p>{vocab.translation}</p>
        </li>)}
    </ul>)

}


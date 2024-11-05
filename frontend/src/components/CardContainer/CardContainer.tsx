import './CardContainer.css'
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    displayedVocab: Vocab | undefined
}


export default function CardContainer(props: Readonly<Props>) {

    return (
        <div id={"card-container"}>
            {props.displayedVocab && <article id={"word-card"}
                      className={"card"}>{props.displayedVocab.word}</article>}
            {props.displayedVocab &&<article id={"translation-card"}
                     className={"card"}>{props.displayedVocab.translation}</article>}
        </div>
    )
}
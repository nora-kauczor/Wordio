import './CardContainer.css'
import {Vocab} from "../../types/Vocab.ts";
type Props = {
    vocab:Vocab
}

// rename ReviewCardContainer
export default function CardContainer(props:Readonly<Props>){
    return(
        <div id={"card-container"}>
            <article id={"word-card"} className={"card"}>{props.vocab.word}</article>
            <article id={"translation-card"} className={"card"}>{props.vocab.translation}</article>
        </div>
    )
}
import './ReviewCardContainer.css'
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    displayedVocab: Vocab | null
}


export default function ReviewCardContainer(props: Readonly<Props>) {
    if (!props.displayedVocab) return <p> Loading...</p>

    return (
        <div id={"card-container"}>
            <article id={"word-card"}
                     className={"card"}>{props.displayedVocab.word}</article>
            <article id={"translation-card"}
                     className={"card"}>{props.displayedVocab.translation}</article>
        </div>
    )
}
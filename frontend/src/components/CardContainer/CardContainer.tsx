import './CardContainer.css'
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    displayedVocab: Vocab | undefined
    displayWord: boolean
}


export default function CardContainer(props: Readonly<Props>) {
    if (!props.displayedVocab) return <p>Loading...</p>
    return (<div id={"card-container"}>
        <article
            className={"review-card + card"}
            aria-labelledby={"word-label"}>
            <h2 id={"word-label"} className={"visually-hidden"}>Word</h2>
            <p> {props.displayWord ? props.displayedVocab.word : undefined}</p>

        </article>
        <article
            className={"review-card  + card"}
            aria-labelledby={"translation-label"}>
            <h2 id={"translation-label"}
                className={"visually-hidden"}>Translation</h2>
            <p> {props.displayedVocab.translation}</p>

        </article>
    </div>)
}
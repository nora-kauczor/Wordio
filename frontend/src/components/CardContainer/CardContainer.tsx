import './CardContainer.css'
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    displayedVocab: Vocab | undefined
    displayWord: boolean
}


export default function CardContainer(props: Readonly<Props>) {
    if (!props.displayedVocab) return <p>loading...</p>
    return (<div id={"card-container"}>
        <article
            className={"card"}
            aria-labelledby={"word-label"}>
            <h2 id={"word-label"} className={"visually-hidden"}>Word</h2>
            {props.displayWord ? props.displayedVocab.word : undefined}
        </article>
        <article
            className={"card"}
            aria-labelledby={"translation-label"}>
            <h2 id={"translation-label"}
                className={"visually-hidden"}>Translation</h2>
            {props.displayedVocab.translation}
        </article>
    </div>)
}
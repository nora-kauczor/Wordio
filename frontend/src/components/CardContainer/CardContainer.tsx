import './CardContainer.css'
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    displayedVocab: Vocab | undefined
}


export default function CardContainer(props: Readonly<Props>) {

    return (<div id={"card-container"}>
        {props.displayedVocab && <article id={"word-card"}
                                          className={"card"}
                                          aria-labelledby={"word-label"}>
            <h2 id={"word-label"} className={"visually-hidden"}>Word</h2>
            {props.displayedVocab.word}</article>}
        {props.displayedVocab && <article id={"translation-card"}
                                          className={"card"}
                                          aria-labelledby={"translation-label"}>
            <h2 id={"translation-label"}
                className={"visually-hidden"}>Translation</h2>
            {props.displayedVocab.translation}
        </article>}
    </div>)
}
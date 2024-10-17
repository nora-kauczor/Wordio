import './ReviewPage.css'
import {useState} from "react";
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    todaysVocabs: Vocab[]
}

export default function ReviewPage(props: Readonly<Props>) {
    const [vocabsToReview, setVocabsToReview] = useState<Vocab[]>(props.todaysVocabs)
    const [currentVocab, setCurrentVocab] = useState<Vocab>(vocabsToReview[0])



    return (
        <div id={"review-page"}>
            {currentVocab &&
                <CardContainer vocab={currentVocab}/>}
            <label htmlFor={"review-input"}></label>
            <input id={"review-input"}/>
        </div>
    )
}
import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    vocabsLeftToReview: Vocab[]
    removeVocabFromVocabsToReview: (_id: string) => void
}

export default function ReviewPage(props: Readonly<Props>) {
    const [currentIndex, setCurrentIndex] = useState<number>(0)
    const [currentVocab, setCurrentVocab] = useState<Vocab>(props.vocabsLeftToReview[0])
    const [userInput, setUserInput] = useState<string>("")

    useEffect(() => {
        setCurrentVocab(props.vocabsLeftToReview[currentIndex])
    }, [currentIndex]);

    function checkAnswer() {
        if (currentVocab.word !== userInput) {
// shiftStartDateOfReview
        }
        setCurrentIndex(currentIndex++)
        props.removeVocabFromVocabsToReview(currentVocab)
    }

    return (
        <div id={"review-page"}>
            {currentVocab &&
                <CardContainer vocab={currentVocab}/>}
            <label htmlFor={"review-input"}></label>
            <input id={"review-input"}
                   onChange={element => setUserInput(element.target.value)}/>
            <button onClick={checkAnswer}>submit</button>
        </div>
    )
}
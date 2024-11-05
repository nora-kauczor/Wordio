import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import useLocalStorageState from "use-local-storage-state";

type Props = {
    vocabsLeftToReview: Vocab[]
    removeVocabFromVocabsToReview: (_id: string | null) => void
    changeReviewDates: (_id: string | null) => void
}

export default function ReviewPage(props: Readonly<Props>) {
    const [currentIndex, setCurrentIndex] = useLocalStorageState("currentIndex",
        {defaultValue: 0});
    const [currentVocab, setCurrentVocab] = useLocalStorageState("currentVocab",
        {defaultValue: props.vocabsLeftToReview[0]});
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
const navigate = useNavigate()

    useEffect(() => {
        setCurrentVocab(props.vocabsLeftToReview[currentIndex])
    }, [currentIndex]);

    function checkAnswer() {
        // inputWithoutExtraSpaces
        if (currentVocab.word.toLowerCase() !== userInput.toLowerCase()) {
            props.changeReviewDates(currentVocab._id)
            navigate(`/display:${currentVocab._id}`)
        }
        else {
                setShowFireworks(true);
                setTimeout(() => {
                    setShowFireworks(false);
                }, 2500);
            getNextVocab()}
        props.removeVocabFromVocabsToReview(currentVocab._id)
    }

    function getNextVocab():void{
        setCurrentIndex(currentIndex + 1)
    }

    return (<div id={"review-page"} role={"main"}>
            {showFireworks && <Confetti/>}
            {currentVocab && <CardContainer displayedVocab={currentVocab}/>}
            <label htmlFor={"review-input"} className={"visually-hidden"}>Your
                answer</label>
            <input id={"review-input"}
                   onChange={element => setUserInput(element.target.value)}
                   aria-label={"Enter your answer"}
                   placeholder={"Type your answer here"}/>
            <button onClick={checkAnswer}aria-label={"Submit your answer"}>submit</button>
        </div>)
}
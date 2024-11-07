import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import useLocalStorageState from "use-local-storage-state";
import {
    getInputWithoutExtraSpaces
} from "../../utils/getInputWithoutExtraSpaces.ts";
import {getRightAnswers} from "../../utils/getRightAnswer.ts";

type Props = {
    vocabsToReview: Vocab[]
    removeVocabFromVocabsToReview: (id: string | null) => void
    changeReviewDates: (id: string | null) => void
    language: string
}

export default function ReviewPage(props: Readonly<Props>) {

    const [currentIndex, setCurrentIndex] = useLocalStorageState("currentIndex",
        {defaultValue: 0});
    const [currentVocab, setCurrentVocab] = useLocalStorageState("currentVocab",
        {defaultValue: props.vocabsToReview[0]});
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        setCurrentVocab(props.vocabsToReview[currentIndex])
    }, [currentIndex]);


    function checkAnswer() {
        const rightAnswers: string[] = getRightAnswers(currentVocab.word, props.language)
        const inputWithoutExtraSpaces: string = getInputWithoutExtraSpaces(
            userInput)
        if (inputWithoutExtraSpaces.toLowerCase() ===
            currentVocab.word.toLowerCase()) {
            setShowFireworks(true);
            setTimeout(() => {
                setShowFireworks(false);
            }, 2500);
            getNextVocab()
        } else {
            props.changeReviewDates(currentVocab.id)
            // TODO debug navigation
            navigate(`/display/:${currentVocab.id}`)
            // TODO how to get back to review page after that
        }
        props.removeVocabFromVocabsToReview(currentVocab.id)
    }

    function getNextVocab(): void {
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
        <button onClick={checkAnswer} aria-label={"Submit your answer"}>submit
        </button>
    </div>)
}
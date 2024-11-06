import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import useLocalStorageState from "use-local-storage-state";

type Props = {
    vocabsToReview: Vocab[]
    removeVocabFromVocabsToReview: (id: string | null) => void
    changeReviewDates: (id: string | null) => void
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

    function getInputWithoutExtraSpaces(userInput: string): string {
        const chars: string[] = userInput.split('');
        let charsWithoutExtraSpaces: string[] = chars;
        let i: number = 0;
        while (charsWithoutExtraSpaces[i] === " ") {
            charsWithoutExtraSpaces.splice(i, 1)
        }
        let k = charsWithoutExtraSpaces.length - 1
        while (chars[k] === " ") {
            charsWithoutExtraSpaces.splice(k, 1)
        }
        let z: number = 0;
        while (z < charsWithoutExtraSpaces.length) {
            if (charsWithoutExtraSpaces[z] === " " &&
                charsWithoutExtraSpaces[z + 1] === " ") {
                charsWithoutExtraSpaces.splice(z + 1, 1)
            } else {
                z++
            }

        }
        return charsWithoutExtraSpaces.reduce((a, b) => a + b, "")
    }

    function checkAnswer() {
        const inputWithoutExtraSpaces: string = getInputWithoutExtraSpaces(
            userInput)
        if (currentVocab.word.toLowerCase() !==
            inputWithoutExtraSpaces.toLowerCase()) {
            props.changeReviewDates(currentVocab.id)
            navigate(`/display:${currentVocab.id}`)
        } else {
            setShowFireworks(true);
            setTimeout(() => {
                setShowFireworks(false);
            }, 2500);
            getNextVocab()
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
import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import {
    getInputWithoutExtraSpaces
} from "./utils/getInputWithoutExtraSpaces.ts";
import {getRightAnswers} from "./utils/getRightAnswer.ts";
import {toast, ToastContainer} from "react-toastify";

type Props = {
    vocabsToReview: Vocab[]
    removeVocabFromVocabsToReview: (id: string | null) => void
    changeReviewDates: (id: string | null) => void
    language: string
}

export default function ReviewPage(props: Readonly<Props>) {
    const [currentVocab, setCurrentVocab] = useState<Vocab>();
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
    const [displayAnswer, setDisplayAnswer] = useState(false);
    const [inputColor, setInputColor] = useState<string>("inherit")
    const navigate = useNavigate()


    useEffect(() => {
        if (inputColor !== "red") {
            setTimeout(() => {
                setCurrentVocab(props.vocabsToReview[0])
            }, 5000);
        }
    }, [props.vocabsToReview]);

    useEffect(() => {
        console.log(currentVocab)
        setUserInput("")
        setInputColor("inherit")
    }, [currentVocab, setCurrentVocab]);

    // TODO funktioniert an sich aber ist manchmal nicht schnell genug
    function getNextVocab(): void {
        if (props.vocabsToReview && props.vocabsToReview[0] !== currentVocab) {
            setCurrentVocab(props.vocabsToReview[0])
            setDisplayAnswer(false)
        }

    }

    if (!currentVocab) return <p
        className={"loading-message"}>Loading...</p>

    function checkAnswer() {
        const rightAnswers: string[] = getRightAnswers(currentVocab.word,
            props.language)
        const rightAnswersLowerCase = rightAnswers.map(
            answer => answer.toLowerCase())
        const inputWithoutExtraSpaces: string = getInputWithoutExtraSpaces(
            userInput)
        if (rightAnswersLowerCase.includes(inputWithoutExtraSpaces)) {
            setDisplayAnswer(true)
            setShowFireworks(true)
            setInputColor("green")
                setTimeout(() => {
                    setShowFireworks(false)
                }, 5000);
                setTimeout(() => {
                    setDisplayAnswer(false)
                }, 5000);
                setTimeout(() => {
                    setInputColor("inherit")
                }, 5000);
            if (props.vocabsToReview.length < 2) {
                setTimeout(() => {
                    navigate("/")
                }, 5000);
            }
        } else {
            props.changeReviewDates(currentVocab.id)
            setInputColor("red")
            setDisplayAnswer(true)
            toast.error("Wrong answer. The review dates for this vocab are being changed.")
            // TODO: evtl toast message: dates werden geändert...
        }

        props.removeVocabFromVocabsToReview(currentVocab.id)
    }



    return (<div id={"review-page"} className={"page"} role={"main"}>
        <ToastContainer autoClose={2000} hideProgressBar={true}/>
        {showFireworks && <Confetti/>}
        {currentVocab && <CardContainer displayedVocab={currentVocab}
                                        displayWord={displayAnswer}/>}
        <label htmlFor={"review-input"} className={"visually-hidden"}>Your
            answer</label>
        <div id={"review-input-and-button-wrapper"}>
            <input id={"review-input"}
                   onChange={element => setUserInput(element.target.value)}
                   value={userInput}
                   className={inputColor}
                   aria-label={"Enter your answer"}
                   placeholder={"Type your answer here"}
                   disabled={displayAnswer}
            />
            {!displayAnswer ?
                <button className={"review-page-button big-button"}
                        onClick={checkAnswer}
                        aria-label={"Submit your answer"}>show answer
                </button> : <button className={"review-page-button"}
                                    onClick={getNextVocab}>next</button>}
        </div>
    </div>)
}
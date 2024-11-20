import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios";

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
    const [showBackButton, setShowBackButton] = useState(false)
    const navigate = useNavigate()

    useEffect(() => {
        if (props.vocabsToReview.length < 1) {
            setShowBackButton(true)
        }
        if (inputColor !== "red" && !showBackButton) {
            setTimeout(() => {
                setCurrentVocab(props.vocabsToReview[0])
            }, 5000);
        }
    }, [props.vocabsToReview]);

    useEffect(() => {
        setUserInput("")
        setInputColor("inherit")
    }, [currentVocab, setCurrentVocab]);

    function getNextVocab(): void {
        if (props.vocabsToReview && props.vocabsToReview[0] !== currentVocab) {
            setCurrentVocab(props.vocabsToReview[0])
            setDisplayAnswer(false)
        }
    }

    async function checkAnswer(id: string, userInput: string): Promise<boolean | undefined> {
        const inputWithCodedSpaces = userInput.replace(/ /g, "%20")
        try
        {
            const response = await axios.get(
                `/api/check/${id}?answer=${inputWithCodedSpaces}`)
            return response.data;
        } catch (error) {
            console.error(error)
            return undefined;
        }
    }

    async function handleClickShowAnswer() {
        if (!currentVocab || !currentVocab.id) {
            return
        }
        const answerCorrect: boolean | undefined = await checkAnswer(
            currentVocab?.id, userInput);
        if (answerCorrect === undefined) {return}
        if (answerCorrect) {
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
            props.removeVocabFromVocabsToReview(currentVocab.id)
        } else {
            setInputColor("red")
            setDisplayAnswer(true)
            toast.error("Oops! Don't worry, you'll make it next time! 💪",
                {icon: false})
            setTimeout(() => {
                props.changeReviewDates(currentVocab.id);
            }, 2200);
        }

    }

    if (!currentVocab && !showBackButton) return <p
        className={"loading-message"}>Loading...</p>

    return (<div id={"review-page"} className={"page"} role={"main"}>
        <div style={{height: "30px"}}/>
        {showFireworks && <Confetti/>}
        <CardContainer displayedVocab={currentVocab}
                       displayWord={displayAnswer}/>
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
            {!displayAnswer && !showBackButton &&
                <button type={"button"}
                    className={"review-page-button big-button"}
                        onClick={handleClickShowAnswer}
                        aria-label={"Submit your answer"}>show answer
                </button>}
            {displayAnswer && !showBackButton &&
                <button
                    className={"review-page-button big-button"}
                        onClick={getNextVocab}>next</button>}
            {showBackButton && <button className={"home-button"}
                                       onClick={() => navigate(
                                           "/")}>Back</button>}
        </div>
    </div>)
}
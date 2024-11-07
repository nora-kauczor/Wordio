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
    // TODO wäre es nicht sinnvoll immer den gleichen index zu nehmen? also
    // z.b. 0 TODO evtl kein local storage da man ja auf der "seite" bleibt?
    const [currentVocab, setCurrentVocab] = useLocalStorageState("currentVocab",
        {defaultValue: props.vocabsToReview[0]});
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
    const [displayAnswer, setDisplayAnswer] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        // sobald vocabsToReview aktualisiert wurden, aktualisiere die
        // currentVocab
        setCurrentVocab(props.vocabsToReview[currentIndex])
    }, [currentIndex]);

    useEffect(() => {
        // sobald die neue vokabel gespeichert wurde, verstecke die
        // antwort-karte
        setDisplayAnswer(false)
    }, [currentVocab]);

    // useEffect(() => {
    //     if (props.vocabsToReview.length < 1 || !currentVocab)
    //         navigate("/")
    // }, []);

    useEffect(() => {
            if (showFireworks) {
                return
            }
            // TODO erst ¨¨berprüfen wenn vocastoreview geupdated sind
            if (props.vocabsToReview.length < 1) {
                navigate("/")
            } else {
                getNextVocab()
            }
        },[showFireworks])

        function getNextVocab(): void {
            setCurrentIndex(0)
        }

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
                setTimeout(() => {
                    setShowFireworks(false)
                }, 2500);
                setTimeout(() => {
                    setDisplayAnswer(false)
                }, 2500);
setUserInput("")
            } else {
                props.changeReviewDates(currentVocab.id)
                // TODO Zeige den Misserfolg irgendwie an
                setDisplayAnswer(true)
            }

            props.removeVocabFromVocabsToReview(currentVocab.id)
        }


        return (<div id={"review-page"} role={"main"}>
            {showFireworks && <Confetti/>}
            {currentVocab && <CardContainer displayedVocab={currentVocab}
                                            displayWord={displayAnswer}/>}
            <label htmlFor={"review-input"} className={"visually-hidden"}>Your
                answer</label>
            <input id={"review-input"}
                   onChange={element => setUserInput(element.target.value)}
                   aria-label={"Enter your answer"}
                   placeholder={"Type your answer here"}
                   disabled={displayAnswer}
            />
            {!displayAnswer ? <button onClick={checkAnswer}
                                      aria-label={"Submit your answer"}>submit
            </button> : <button onClick={getNextVocab}>next</button>}
        </div>)
    }
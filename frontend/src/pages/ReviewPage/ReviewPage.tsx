import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';

type Props = {
    vocabsLeftToReview: Vocab[]
    removeVocabFromVocabsToReview: (_id: string | null) => void
    changeReviewDates: (_id: string | null) => void
}

export default function ReviewPage(props: Readonly<Props>) {
    // TODO localstorage
    const [currentIndex, setCurrentIndex] = useState<number>(0)
    // TODO localstorage
    const [currentVocab, setCurrentVocab] = useState<Vocab>(props.vocabsLeftToReview[0])
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
const navigate = useNavigate()

    useEffect(() => {
        setCurrentVocab(props.vocabsLeftToReview[currentIndex])
    }, [currentIndex]);

    function checkAnswer() {
        if (currentVocab.word !== userInput) {
            props.changeReviewDates(currentVocab._id)
            navigate(`/display:${currentVocab._id}`)
            // TODO auf displayseite möglichkeit zurückzukommen? go to back to review page and look at the next vocab
            // TODO besser wüare wenn das einfach auch hier angezeigt wird, anstelle des inputfeldes
            // TODO card container etc. checken
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

    return (
        <div id={"review-page"}>
            {showFireworks && <Confetti />}
            {currentVocab &&
                <CardContainer displayedVocab={currentVocab}/>}
            <label htmlFor={"review-input"}></label>
            <input id={"review-input"}
                   onChange={element => setUserInput(element.target.value)}/>
            <button onClick={checkAnswer}>submit</button>
        </div>
    )
}
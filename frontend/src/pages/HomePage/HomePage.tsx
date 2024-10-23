import './HomePage.css'
import NewVocabsPopUp
    from "../../components/NewVocabsPopUp/NewVocabsPopUp.tsx";
import {useNavigate} from "react-router-dom";
import {useState} from "react";

type Props = {
    finishedReviewing: boolean
}
export default function HomePage(props: Readonly<Props>) {
    const [showPopUp, setShowPopUp] = useState(false)
    const navigate = useNavigate()

    return (
        <div id={"homepage"}>
            <article id={"homepage-text-and-button"}>
                {props.finishedReviewing ?
                    <p>Great job! You've already finished
                        reviewing
                        your vocabulary for today. To start
                        learning
                        more words click below </p> : <p>
                        Nice to see you! Click below to get
                        started with your daily vocab
                        review</p>}
                {props.finishedReviewing ? <button
                    onClick={() => navigate("/review")}
                    onKeyDown={() => navigate("/review")}>Learn
                    more</button> : <button
                    onClick={() => setShowPopUp(true)}
                    onKeyDown={() => setShowPopUp(true)}>
                    Review</button>}
            </article>
            {showPopUp && <NewVocabsPopUp/>}
        </div>
    )
}
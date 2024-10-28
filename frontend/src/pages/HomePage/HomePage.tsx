import './HomePage.css'
import NewVocabsPopUp
    from "../../components/NewVocabsPopUp/NewVocabsPopUp.tsx";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    finishedReviewing: boolean
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    vocabs:Vocab[]
}
export default function HomePage(props: Readonly<Props>) {
    const [showPopUp, setShowPopUp] = useState(false)
    const navigate = useNavigate()

    return (
        <div id={"homepage"}>
            <article id={"homepage-text-and-button"}>
                {props.finishedReviewing ?
                    <p>You've got no vocabulary scheduled
                        for today or you've already reviewed
                        all of it. To start learning
                        (more) words click below </p> : <p>
                        Nice to see you! Click below to get
                        started with your daily vocab
                        review</p>}
                {props.finishedReviewing ?
                    <button
                        onClick={() => setShowPopUp(true)}
                        onKeyDown={() => setShowPopUp(true)}>
                        New vocabulary</button>
                    :
                    <button
                        onClick={() => navigate("/review")}
                        onKeyDown={() => navigate("/review")}>Review
                    </button>
                }
            </article>
            {
                showPopUp && <NewVocabsPopUp
                vocabs={props.vocabs}
                    setUseForm={props.setUseForm}
                    setShowPopUp={setShowPopUp}/>}
        </div>
    )
}
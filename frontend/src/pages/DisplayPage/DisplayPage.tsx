import './DisplayPage.css'
import ReviewCardContainer
    from "../../components/ReviewCardContainer/ReviewCardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";


type Props = {
    vocabs?: Vocab[]
    vocab?: Vocab | null
}

export default function DisplayPage(props: Readonly<Props>) {
    const [displayedVocab, setDisplayedVocab] = useState<Vocab>()
    const [randomMode, setRandomMode] = useState<boolean>(false)
    const navigate = useNavigate();
    useEffect(() => {
        if (props.vocab) {
            setDisplayedVocab(props.vocab)
        }
        setDisplayedVocab(getRandomVocab())
        setRandomMode(true)
    }, []);


    if (!props.vocabs && randomMode) return <p> Loading...</p>
    if (!props.vocab && !randomMode) return <p> Loading...</p>

    function getRandomVocab():Vocab | null{
        if (!props.vocabs) {return null}
        const numberOfVocabs: number = props.vocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs)-1
        console.log(props.vocabs[randomIndex])
        return props.vocabs[randomIndex];
    }



    return (
        <div id={"display-page"}>
            <p>You've just activated this vocab. Take your time to memorize it:</p>
            <ReviewCardContainer displayedVocab={displayedVocab}/>
            <p>Finished? Click the button below to go bakc to the homepage</p>
            <button onClick={()=>navigate("/")}>Go back to Home</button>
        </div>
    )
}
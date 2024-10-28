import './DisplayPage.css'
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";


type Props = {
    vocabs?: Vocab[]
    vocab?: Vocab | null
}

export default function DisplayPage(props: Readonly<Props>) {
    const [displayedVocab, setDisplayedVocab] = useState<Vocab>()
    const [randomMode, setRandomMode] = useState<boolean>(false)
    const navigate = useNavigate()
    const location = useLocation()
    console.log(location)
    useEffect(() => {
        // location auslesen und desmentsprechend die richtige vocab als displayed vocab einfügen


        // if (props.vocab) {
        //     setDisplayedVocab(props.vocab)
        // }
        // setDisplayedVocab(getRandomVocab())
        // setRandomMode(true)
    }, []);


    if (!props.vocabs && randomMode) return <p> Loading...</p>
    if (!props.vocab && !randomMode) return <p> Loading...</p>





    return (
        <div id={"display-page"}>
            <p>You've just activated this vocab. Take your time to memorize it:</p>
            <CardContainer displayedVocab={displayedVocab}/>
            <p>Finished? Click the button below to go bakc to the homepage</p>
            <button onClick={()=>navigate("/")}>Go back to Home</button>
        </div>
    )
}
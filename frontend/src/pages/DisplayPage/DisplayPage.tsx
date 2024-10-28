import './DisplayPage.css'
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";

type Props = {
    vocabs:Vocab[]
}

export default function DisplayPage(props: Readonly<Props>) {
const [displayedVocab, setDisplayedVocab] = useState<Vocab>()
    const navigate = useNavigate()
    const location = useLocation()

    function getVocabToBeDisplayed(){
        const path = location.pathname
        const _id = path.substring(9)
        return props.vocabs.find(vocab => vocab._id === _id)
    }

    useEffect(() => {
      setDisplayedVocab(getVocabToBeDisplayed())
    }, []);

if (!props.vocabs){<p>Loading...</p>}

    return (
        <div id={"display-page"}>
            <p>You've just activated this vocab. Take your time to memorize it:</p>
            <CardContainer displayedVocab={displayedVocab}/>
            <p>Finished? Click the button below to go bakc to the homepage</p>
            <button onClick={()=>navigate("/")}>Go back to Home</button>
        </div>
    )
}
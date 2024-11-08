import './DisplayPage.css'
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import {
    useNavigate,
    useParams
} from "react-router-dom";

type Props = {
    vocabs:Vocab[]
}

export default function DisplayPage(props: Readonly<Props>) {
const [displayedVocab, setDisplayedVocab] = useState<Vocab>()
    const navigate = useNavigate()
    const { id } = useParams()

    useEffect(() => {
        if (!props.vocabs){return}
      setDisplayedVocab(props.vocabs.find(vocab => vocab.id === id?.substring(1)))
    }, [id, props.vocabs]);

if (!props.vocabs) return <p className={"loading-message"}>Loading...</p>


    return (
        <div id={"display-page"} className={"page"} role={"main"}>
            <p>You've just activated this vocab. Take your time to memorize it:</p>
            <CardContainer displayedVocab={displayedVocab} displayWord={true}/>
            <p>Finished? Click the button below to go back to the homepage</p>
            <button onClick={()=>navigate("/")}
                    aria-label={"Go back to the homepage"}>Go back to Home</button>
        </div>
    )
}
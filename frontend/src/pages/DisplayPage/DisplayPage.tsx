import './DisplayPage.css'
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import React, {useEffect, useState} from "react";
import {
    useNavigate,
    useParams
} from "react-router-dom";

type Props = {
    vocabs:Vocab[]
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
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

    function handleClick(){
        navigate("/")
        props.setDisplayNewVocabsPopUp(false)
    }

    return (<div id={"display-page"} className={"page"} role={"main"}>
            <div style={{height: "40px"}}/>
            <p id={"display-page-text"}> Take your time to memorize
                the new vocab:</p>
            <CardContainer displayedVocab={displayedVocab} displayWord={true}/>
            <button onClick={handleClick}
                    aria-label={"Go back to the homepage"}
                    className={"home-button"}>Home
            </button>
        </div>)
}
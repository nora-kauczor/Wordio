import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";
import React from "react";

type Props = {
    vocabs: Vocab[]
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    userId: string | undefined
    activateVocab: (id: string) => void
    allVocabsActivated: boolean
}

export default function NewVocabsPopUp(props: Readonly<Props>) {
    const navigate = useNavigate()

    function handleClick() {
        props.setDisplayNewVocabsPopUp(false)
        props.setUseForm(true)
    }

    function getRandomVocab(): Vocab | undefined {
        if (!props.userId) {
            console.error(
                "Couldn't get random vocab because userId was not available");
            return
        }
        const inactiveVocabs: Vocab[] = props.vocabs
            .filter(vocab => {
                if (!vocab.datesPerUser) {
                    return true
                }
                if (props.userId && !vocab.datesPerUser[props.userId]) {
                    return true
                }
                if (props.userId && vocab.datesPerUser[props.userId].length <
                    1) {
                    return true
                }
            })
        const numberOfVocabs: number = inactiveVocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs) - 1
        return props.vocabs[randomIndex];
    }

    function activateAndGoToDisplayPageWithRandomVocab() {
        const randomVocab = getRandomVocab()
        if (!randomVocab || !randomVocab.id) {
            return
        }
        const id = randomVocab.id
        props.activateVocab(id)
        navigate(`/display/:${id}`)
    }


    return (<div id={"new-vocabs-popup"}
                 className={"pop-up"}>
        <button onClick={() => props.setDisplayNewVocabsPopUp(false)}
                className={"close-button"}
                aria-label={"Close new vocabulary pop-up"}>✕
        </button>
        <h2 className={"popup-header"}>New vocabulary</h2>

        <div id={"button-wrapper"}>
            <button className={"new-vocabs-button"}
                    onClick={handleClick}
                    aria-label={"Enter new vocabulary"}
            >Enter new
                vocab
            </button>
            {!props.allVocabsActivated &&
                <button className={"new-vocabs-button"}
                        onClick={() => navigate("/backlog")}
                        aria-label={"Pick from backlog"}
                >Pick
                    from backlog
                </button>}
            {!props.allVocabsActivated &&
                <button className={"new-vocabs-button"}
                        onClick={activateAndGoToDisplayPageWithRandomVocab}
                        aria-label={"Get random vocabulary"}
                >Get random
                    vocab
                </button>}
        </div>
    </div>)
}
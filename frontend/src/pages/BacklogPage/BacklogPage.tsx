import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

type Props = {
    vocabs: Vocab[]
    deleteVocab: (_id: string) => void
}

export default function BacklogPage(props: Readonly<Props>) {
    const [introduction, setIntroduction] = useState<String>(regularIntroduction)

    const regularIntroduction: string = "Browse the vocabulary you haven't activated, yet.\n" +
        "            Get an overview of your planned workload, delete\n" +
        "            vocabs that you think aren't necessary" +
        "            or pick a new one you want to activate."

    const newVocabIntroduction: string = "Click on a vocabulary to start learning."

    const navigate = useNavigate();

    // const handleClick = () => {
    //     navigate('/review');
    // };

    return (<div id={"backlog-page"}>
        <p>{introduction}</p>
        <ul id={"backlog-list"}>
            {props.vocabs.map(vocab => <li key={vocab._id}
                                           className={"backlog-list-item"}
            // onClick={handleClick}
            >
                <p>{vocab.word}</p>
                <p>{vocab.translation}</p>
            </li>)}
        </ul>
    </div>)

}
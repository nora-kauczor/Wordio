import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import VocabList
    from "../../components/VocabList/VocabList.tsx";

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
<VocabList vocabs={props.vocabs}/>
    </div>)

}
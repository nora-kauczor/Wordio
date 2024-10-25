import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList
    from "../../components/VocabList/VocabList.tsx";

type Props = {
    vocabs: Vocab[]
    deleteVocab: (_id: string) => void
}

export default function BacklogPage(props: Readonly<Props>) {

    return (<div id={"backlog-page"}>
<VocabList vocabs={props.vocabs}/>
    </div>)

}
import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
import VocabList
    from "../../components/VocabList/VocabList.tsx";

type Props = {
    vocabs: Vocab[]
    deleteVocab: (_id: string) => void
    activateVocab: (_id: string) => void
    language:string
}

export default function BacklogPage(props: Readonly<Props>) {

    return (<div id={"backlog-page"}>
        <VocabList vocabs={props.vocabs}
                   calendarMode={false}
                   activateVocab={props.activateVocab}
                   deleteVocab={props.deleteVocab}/>
    </div>)

}

import './BacklogPage.css'
import {Vocab} from "../../types/Vocab.ts";
type Props = {
    vocabs:Vocab[]
    deleteVocab:(_id:string) => void
}

export default function BacklogPage(props:Readonly<Props>){

    return (<div id={"backlog-page"}>
        <ul id={"backlog-page-list"}>

        </ul>

    </div>)

}
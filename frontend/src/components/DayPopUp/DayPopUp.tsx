import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../VocabList/VocabList.tsx";

type Props = {
    vocabsOfDay: Vocab[]
}

export default function DayPopUp(props: Readonly<Props>) {

    return(
        <VocabList vocabs={props.vocabsOfDay}/>
    )


}

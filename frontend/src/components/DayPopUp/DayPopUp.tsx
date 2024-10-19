import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../VocabList/VocabList.tsx";

type Props = {
    vocabsOfDay: Vocab[]
    closeDayPopUp: () => void
}

export default function DayPopUp(props: Readonly<Props>) {

    return(<div>
        <VocabList vocabs={props.vocabsOfDay}/>
            <button>close</button>
        </div>
    )


}

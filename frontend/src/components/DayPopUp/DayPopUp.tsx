import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../VocabList/VocabList.tsx";
import './DayPopUp.css'
type Props = {
    day:string
    vocabsOfDay: Vocab[]
    closeDayPopUp: () => void
}

export default function DayPopUp(props: Readonly<Props>) {
    const displayDate:string = props.day.substring(8, 10)+"."
        +props.day.substring(5,7)+"."+props.day.substring(0,4)
    return(<div id={"day-popup"}>
            <h3>{displayDate}</h3>
        <VocabList vocabs={props.vocabsOfDay}/>
            <button>close</button>
        </div>
    )


}

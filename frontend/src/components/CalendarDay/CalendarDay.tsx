import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
import './CalendarDay.css'
import { RxDotFilled } from "react-icons/rx";


type Props = {
    vocabIdsOfDate: VocabIdsOfDate | null
    openDayPopUpAndPassItVocabs: (vocabIdsOfDate: VocabIdsOfDate) => void
}

export default function CalendarDay(props: Readonly<Props>) {
    const displayDate:string = props.vocabIdsOfDate?.date?.substring(8, 10)+"."
        +props.vocabIdsOfDate?.date?.substring(5,7)+"."

console.log(props.vocabIdsOfDate)
    return (
        <div id={"calendar-day-wrapper"}>
            {props.vocabIdsOfDate && <button id={"calendar-day"}
                     onClick={() => props.vocabIdsOfDate && props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}
                     onKeyDown={() => props.vocabIdsOfDate && props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}>
                <p> {displayDate}</p>
                <p>{props.vocabIdsOfDate?.vocabIds?.length} Vocabs</p>
            </button>}
        </div>
    )
}


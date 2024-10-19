import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";


type Props = {
    vocabIdsOfDate: VocabIdsOfDate
    openDayPopUpAndPassItVocabs: (vocabIdsOfDate: VocabIdsOfDate) => void
}

export default function CalendarDay(props: Readonly<Props>) {
    return (<button id={"calendar-day"} onClick={()=>props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}
                    onKeyDown={()=>props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}>
        <p> {props.vocabIdsOfDate.date}</p>
        <p>{props.vocabIdsOfDate.vocabIds.length + 1} Vocabs</p>
    </button>)
}
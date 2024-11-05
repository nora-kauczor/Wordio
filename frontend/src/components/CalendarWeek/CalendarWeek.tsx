
import CalendarDay from "../CalendarDay/CalendarDay.tsx";
import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
import './CalendarWeek.css'
import {uid} from "uid";

type Props = {
    vocabIdsOfWeek: VocabIdsOfDate[]
    openDayPopUpAndPassItVocabs: (vocabIdsOfDate: VocabIdsOfDate) => void
}
export default function CalendarWeek(props: Readonly<Props>) {

    return(
        <ul id={"calendar-week"} role="list">
            {props.vocabIdsOfWeek.length > 0 && props.vocabIdsOfWeek.map(vocabIdsOfDate =>
                vocabIdsOfDate ?
                <CalendarDay key={vocabIdsOfDate.date}
                    vocabIdsOfDate={vocabIdsOfDate}
                             openDayPopUpAndPassItVocabs={props.openDayPopUpAndPassItVocabs}/> :
                    <CalendarDay  key={uid()}
                                                                                                               vocabIdsOfDate={null}
                                                                                                               openDayPopUpAndPassItVocabs={props.openDayPopUpAndPassItVocabs}/>) }
        </ul>
    )
}



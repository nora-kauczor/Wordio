import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";


type Props = {
    vocabIdsOfDate: VocabIdsOfDate
}

export default function CalendarDay(props: Readonly<Props>) {
    // on click: DayPopUp öffnet sich mit Liste der Vocabs, dafür mit den Ids die Vocabs rausfiltern
    // diese funktionalität am besten in calendarpage
    return (<div id={"calendar-day"}>
        <p> {props.vocabIdsOfDate.date}</p>
        <p>{props.vocabIdsOfDate.vocabIds.length + 1} Vocabs</p>
    </div>)
}
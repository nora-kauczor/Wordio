
import CalendarDay from "../CalendarDay/CalendarDay.tsx";
import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
type Props = {
    vocabIdsOfWeek: VocabIdsOfDate[]
}
export default function CalendarWeek(props: Readonly<Props>) {

    return(
        <ul id={"calendar-week"}>
            {props.vocabIdsOfWeek.map(vocabIdsOfDate => <CalendarDay vocabIdsOfDate={vocabIdsOfDate}/>) }
        </ul>
    )
}
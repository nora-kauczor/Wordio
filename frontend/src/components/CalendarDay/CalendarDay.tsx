import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
import './CalendarDay.css'

import {GoDotFill} from "react-icons/go";
import {FaPlus} from "react-icons/fa";

type Props = {
    vocabIdsOfDate: VocabIdsOfDate | null
    openDayPopUpAndPassItVocabs: (vocabIdsOfDate: VocabIdsOfDate) => void
}

export default function CalendarDay(props: Readonly<Props>) {

    const displayDate: string = props.vocabIdsOfDate?.date?.substring(8, 10) +
        "." + props.vocabIdsOfDate?.date?.substring(5, 7) + "."
    const numberOfVocabs = props.vocabIdsOfDate?.vocabIds ?
        props.vocabIdsOfDate?.vocabIds?.length : 0;

    return (<div id={"calendar-day-wrapper"}>
        {props.vocabIdsOfDate && <button id={"calendar-day"}
                                         disabled={numberOfVocabs < 1}
                                         className={numberOfVocabs > 0 ? '' :
                                             'empty-day'}
                                         onClick={() => props.vocabIdsOfDate &&
                                             props.openDayPopUpAndPassItVocabs(
                                                 props.vocabIdsOfDate)}
                                         aria-label={`Open details for ${displayDate}`}
                                         aria-disabled={!props.vocabIdsOfDate}>
            <p id={"calendar-date"}> {displayDate}</p>
            <div id={"dot-container"}
                 aria-hidden={!numberOfVocabs}>
                {numberOfVocabs > 0 ? <GoDotFill id={"dot-1"} className={"dot"}
                                                 aria-label={"1 vocab item available"}/> :
                    <GoDotFill id={"invisible-dot"} className={"dot"}
                               aria-hidden={"true"}/>}
                {numberOfVocabs > 1 && <GoDotFill id={"dot-2"}
                                                  className={"dot"}
                                                  aria-label={"2 vocab items available"}/>}
                {numberOfVocabs > 2 && <GoDotFill
                    id={"dot-3"}
                    className={"dot"}
                    aria-label={"3 vocab items available"}/>}
                {numberOfVocabs > 3 && <GoDotFill
                    id={"dot-4"}
                    className={"dot"}
                    aria-label={"4 vocab items available"}/>}
                {numberOfVocabs > 4 && <GoDotFill
                    id={"dot-5"} className={"dot"}
                    aria-label={"5 vocab items available"}/>}
                {numberOfVocabs > 5 && <FaPlus id={"plus"} className={"dot"}
                                               aria-label={"More than 5 vocab items available"}/>}
            </div>
        </button>}
    </div>)
}


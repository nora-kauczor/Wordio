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

    return (<div id={"calendar-day-wrapper"} >
            {props.vocabIdsOfDate && <button id={"calendar-day"}
                                             className={props.vocabIdsOfDate?.vocabIds?.length >
                                             0 ? 'dots' : 'no-dots'}
                                             onClick={() => props.vocabIdsOfDate &&
                                                 props.openDayPopUpAndPassItVocabs(
                                                     props.vocabIdsOfDate)}
                                             onKeyDown={(e) => {
                                                 if (e.key === 'Enter' ||
                                                     e.key === ' ') {
                                                     props.openDayPopUpAndPassItVocabs(
                                                         props.vocabIdsOfDate!);
                                                 }
                                             }}
                                             aria-label={`Open details for ${displayDate}`}
                                             aria-disabled={!props.vocabIdsOfDate}>
                <p id={"calendar-date"}> {displayDate}</p>
                <div id={"dot-container"}
                     aria-hidden={!props.vocabIdsOfDate?.vocabIds?.length}>
                    {props.vocabIdsOfDate?.vocabIds?.length > 0 ?
                        <GoDotFill id={"dot-1"} className={"dot"}
                                   aria-label={"1 vocab item available"}/> :
                        <GoDotFill id={"invisible-dot"} className={"dot"}
                                   aria-hidden={"true"}/>}
                    {props.vocabIdsOfDate?.vocabIds?.length > 1 &&
                        <GoDotFill id={"dot-2"}
                                   className={"dot"}
                                   aria-label={"2 vocab items available"}/>}
                    {props.vocabIdsOfDate?.vocabIds?.length > 2 && <GoDotFill
                        id={"dot-3"}
                        className={"dot"}
                        aria-label={"3 vocab items available"}/>}
                    {props.vocabIdsOfDate?.vocabIds?.length > 3 && <GoDotFill
                        id={"dot-4"}
                        className={"dot"}
                        aria-label={"4 vocab items available"}/>}
                    {props.vocabIdsOfDate?.vocabIds?.length > 4 && <GoDotFill
                        id={"dot-5"} className={"dot"}
                        aria-label={"5 vocab items available"}/>}
                    {props.vocabIdsOfDate?.vocabIds?.length > 5 &&
                        <FaPlus id={"plus"} className={"dot"}
                                aria-label={"More than 5 vocab items available"}/>}
                </div>
            </button>}
        </div>)
}


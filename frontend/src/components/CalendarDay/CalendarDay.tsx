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
    const displayDate: string = props.vocabIdsOfDate?.date?.substring(8, 10) + "."
        + props.vocabIdsOfDate?.date?.substring(5, 7) + "."

    return (
        <div id={"calendar-day-wrapper"}>
            {props.vocabIdsOfDate &&
                <button id={"calendar-day"} className={props.vocabIdsOfDate?.vocabIds?.length > 0 ? 'dots' : 'no-dots'}
                        onClick={() => props.vocabIdsOfDate && props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}
                        onKeyDown={() => props.vocabIdsOfDate && props.openDayPopUpAndPassItVocabs(props.vocabIdsOfDate)}>
                    <p id={"calendar-date"} > {displayDate}</p>
                    <div id={"dot-container"}>
                        {props.vocabIdsOfDate?.vocabIds?.length > 0 ? <GoDotFill id={"dot-1"}
                                   className={"dot"}/> : <GoDotFill id={"invisible-dot"}
                                                                    className={"dot"}/>}
                        {props.vocabIdsOfDate?.vocabIds?.length > 1 && <GoDotFill id={"dot-2"}
                                   className={"dot"}/>}
                        {props.vocabIdsOfDate?.vocabIds?.length > 2 && <GoDotFill
                        id={"dot-3"}
                        className={"dot"}/>}
                        {props.vocabIdsOfDate?.vocabIds?.length > 3 && <GoDotFill
                        id={"dot-4"}
                        className={"dot"}/>}
                        {props.vocabIdsOfDate?.vocabIds?.length > 4 && <GoDotFill
                        id={"dot-5"} className={"dot"}/>}
                        {props.vocabIdsOfDate?.vocabIds?.length > 5 &&  <FaPlus id={"plus"} className={"dot"}/>}
                    </div>
                </button>}
        </div>
    )
}


import './CalendarPage.css'
import {useEffect, useState} from "react";
import axios from "axios";
import CalendarWeek
    from "../../components/CalendarWeek/CalendarWeek.tsx";
import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
import {Vocab} from "../../types/Vocab.ts";
import DayPopUp
    from "../../components/DayPopUp/DayPopUp.tsx";
import {uid} from 'uid';
import {
    Month,
} from "../../types/Month.ts";


type Props = {
    vocabs: Vocab[]
    deactivateVocab: (_id: string) => void
    language: string
}

export default function CalendarPage(props: Readonly<Props>) {
    const [month, setMonth] = useState<Month>()
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] =
        useState<Vocab[]>([])
    const [dayOfDayPopUp, setDayOfDayPopUp] =
        useState<string>("")

    useEffect(() => {
        if (!props.language) {return}
        getMonth()
    }, []);

    if (!month) {
        return <p>Loading calendar...</p>
    }


function getMonth() {
        const today = new Date();
        const year = today.getFullYear().toString();
        const month = (today.getMonth() + 1).toString();
        axios.get(`/api/calendar?year=${year}&month=${month}
        &language=${props.language}`)
            .then(response => setMonth(response.data))
            .catch(error => console.error(error))
    }

    function changeMonth(clickedButton: string):void {
        if (!month) {
            return
        }
        const currentYear: string | undefined =
            month.vocabIdsOfMonth[1][0].date.substring(0, 4)
        const currentMonth: string | undefined =
            month.vocabIdsOfMonth[1][0].date.substring(5, 9)
        const currentMonthNumber: number =
            parseInt(currentMonth)
        const currentYearNumber: number =
            parseInt(currentYear)
        let newMonth: number = 0
        let newYear: number = 0
        if (clickedButton === "previous") {
            newMonth = currentMonthNumber > 1 ?
                (currentMonthNumber - 1) : 12
            newYear = currentMonthNumber > 1 ?
                currentYearNumber : (currentYearNumber - 1)
        } else {
            newMonth = currentMonthNumber < 12 ?
                (currentMonthNumber + 1) : 1
            newYear = currentMonthNumber < 12 ?
                currentYearNumber : (currentYearNumber + 1)
        }
        axios.get(`/api/calendar?year=${newYear.toString()}&
        month=${newMonth.toString()}&language=${props.language}`)
            .then(response => setMonth(response.data))
            .then(response => console.log(response))
            .catch(error => console.error(error))
    }

    function openDayPopUpAndPassItVocabs(
        vocabIdsOfDate: VocabIdsOfDate): void {
        if (!vocabIdsOfDate.date) {
            return
        }
        setDayOfDayPopUp(vocabIdsOfDate.date)
        const ids: string[] | null = vocabIdsOfDate.vocabIds;
        const vocabs: Vocab[] = props.vocabs.filter(
            vocab => vocab._id && ids?.includes(vocab._id))
        setVocabsOfDayPopUp(vocabs)
    }

    function closeDayPopUp(): void {
        setVocabsOfDayPopUp([])
        setDayOfDayPopUp("")
    }

    return (
        <div id={"calendar-page"}>
            <div id={"button-and-header-container"}>
                <button
                    className={"calendar-button"}
                    onClick={() => changeMonth("previous")}
                    onKeyDown={() => changeMonth("previous")}>◀︎
                </button>
                {month && <h2>{month.yearMonthName}</h2>}
                <button className={"calendar-button"}
                        onClick={() => changeMonth("next")}
                        onKeyDown={() => changeMonth("next")}>▶︎
                </button>
            </div>
            <article id={"weeks-wrapper"}>
                {month && month.vocabIdsOfMonth.map(
                    vocabIdsOfWeek =>
                    <CalendarWeek
                        key={uid()}
                        vocabIdsOfWeek={vocabIdsOfWeek}
                        openDayPopUpAndPassItVocabs={
                        openDayPopUpAndPassItVocabs}/>)}
            </article>
            {vocabsOfDayPopUp.length > 0 &&
                <DayPopUp
                    day={dayOfDayPopUp}
                    vocabsOfDay={vocabsOfDayPopUp}
                    closeDayPopUp={closeDayPopUp}
                    deactivateVocab={props.deactivateVocab}/>}
        <button id={"back-button"}>← Back</button>
        </div>
    )
}
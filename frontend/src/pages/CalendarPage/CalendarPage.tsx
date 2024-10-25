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
    VocabIdsOfYearMonth
} from "../../types/VocabIdsOfYearMonth.ts";


type Props = {
    vocabs: Vocab[]
}

export default function CalendarPage(props: Readonly<Props>) {
    const [vocabIdsOfYearMonth, setVocabIdsOfYearMonth] = useState<VocabIdsOfYearMonth>([])
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])
    const [dayOfDayPopUp, setDayOfDayPopUp] = useState<string>("")
    const [monthHeader, setMonthHeader] = useState<string>("")

    useEffect(() => {
        getVocabIdsOfCurrentYearMonth()
    }, []);

    if (vocabIdsOfYearMonth.length === 0) {
        return <p>Loading calendar...</p>
    }

    async function getVocabIdsOfCurrentYearMonth() {
        const today = new Date();
        const year = today.getFullYear().toString();
        const month = (today.getMonth() + 1).toString();
        setMonthHeader(getMonthHeader(month, year))
        axios.get(`/api/calendar?year=${year}&month=${month}`)
            .then(response => setVocabIdsOfYearMonth(response.data))
            .catch(error => console.error(error))
    }

    function changeMonth(buttonPressed: string) {
        const currentYear: string | undefined = vocabIdsOfYearMonth[1][0].date?.substring(0, 4)
        const currentMonth: string | undefined = vocabIdsOfYearMonth[1][0].date?.substring(5, 9)
        const currentMonthNumber: number = parseInt(currentMonth)
        const currentYearNumber: number = parseInt(currentYear)
        let month: number = 0
        let year: number = 0
        if (buttonPressed === "previous") {
            month = currentMonthNumber > 1 ? (currentMonthNumber - 1) : 12
            year = currentMonthNumber > 1 ? currentYearNumber : (currentYearNumber - 1)
        }
      else {
            month = currentMonthNumber < 12 ? (currentMonthNumber + 1) : 1
            year = currentMonthNumber < 12 ? currentYearNumber : (currentYearNumber + 1)
        }
        axios.get(`/api/calendar?year=${year.toString()}&month=${month.toString()}`)
            .then(response => setVocabIdsOfYearMonth(response.data))
            .catch(error => console.error(error))
        setMonthHeader(getMonthHeader(month.toString(), year.toString()))
    }

    function getMonthHeader(month: string, year: string): string {
        if (vocabIdsOfYearMonth.length === 0) {
            return ""
        }
        const months: string[] = [
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        ];
        return months[Number(month) - 1] + " " + year
    }

    function openDayPopUpAndPassItVocabs(vocabIdsOfDate: VocabIdsOfDate): void {
       if (!vocabIdsOfDate.date){return}
        setDayOfDayPopUp(vocabIdsOfDate.date)
        const ids: string[] | null = vocabIdsOfDate.vocabIds;
        const vocabs: Vocab[] = props.vocabs.filter(vocab => vocab._id && ids?.includes(vocab._id))
        setVocabsOfDayPopUp(vocabs)
    }

    function closeDayPopUp(): void {
        console.log("closeDayPopUp was clicked")
        setVocabsOfDayPopUp([])
        setDayOfDayPopUp("")
    }

    return (
        <div id={"calendar-page"}>
            <div id={"button-container"}>
                <button
                    onClick={() => changeMonth("previous")}
                    onKeyDown={() => changeMonth("previous")}>previous
                </button>
                <button onClick={() => changeMonth("next")}
                        onKeyDown={() => changeMonth("next")}>next
                </button>
            </div>
            <h2>{monthHeader}</h2>
            <article id={"weeks-wrapper"}>
            {vocabIdsOfYearMonth.length > 0 && vocabIdsOfYearMonth.map(vocabIdsOfWeek =>
                <CalendarWeek
                    key={uid()}
                    vocabIdsOfWeek={vocabIdsOfWeek}
                    openDayPopUpAndPassItVocabs={openDayPopUpAndPassItVocabs}/>)}
            </article>
            {vocabsOfDayPopUp.length > 0 &&
                <DayPopUp
                day={dayOfDayPopUp}
                    vocabsOfDay={vocabsOfDayPopUp}
                          closeDayPopUp={closeDayPopUp}/>}
        </div>
    )
}
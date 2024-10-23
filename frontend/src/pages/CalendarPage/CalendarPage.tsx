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


type Props = {
    vocabs: Vocab[]
}

export default function CalendarPage(props: Readonly<Props>) {
    const [vocabIdsOfYearMonth, setVocabIdsOfYearMonth] = useState<VocabIdsOfDate[]>([])
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])
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

    function goToNextYearMonth() {
        const currentYear: string | undefined = vocabIdsOfYearMonth[1][0].date?.substring(0, 4)
        const currentMonth: string | undefined = vocabIdsOfYearMonth[1][0].date?.substring(5,9)
        const currentMonthNumber: number = parseInt(currentMonth)
        const currentYearNumber: number = parseInt(currentYear)
        console.log(currentMonthNumber)
        const month: string = currentMonthNumber < 12 ? (currentMonthNumber + 1).toString() : "1"
        const year: string | undefined = currentMonthNumber < 12 ? currentYearNumber.toString() : (currentYearNumber + 1).toString()
        axios.get(`/api/calendar?year=${year}&month=${month}`)
            .then(response => setVocabIdsOfYearMonth(response.data))
            .catch(error => console.error(error))
        setMonthHeader(getMonthHeader(month, year))
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
        const ids: string[] | null = vocabIdsOfDate.vocabIds;
        const vocabs: Vocab[] = props.vocabs.filter(vocab => vocab._id && ids?.includes(vocab._id))
        setVocabsOfDayPopUp(vocabs)
    }

    function closeDayPopUp(): void {
        setVocabsOfDayPopUp([])
    }

    return (
        <div id={"calendar-page"}>
            <div id={"button-container"}>
                <button>previous</button>
                <button onClick={goToNextYearMonth}>next
                </button>
            </div>
            <h2>{monthHeader}</h2>
            {vocabIdsOfYearMonth.length > 0 && vocabIdsOfYearMonth.map(vocabIdsOfWeek =>
                <CalendarWeek
                    key={uid()}
                    vocabIdsOfWeek={vocabIdsOfWeek}
                    openDayPopUpAndPassItVocabs={openDayPopUpAndPassItVocabs}/>)}
            {vocabsOfDayPopUp.length > 0 &&
                <DayPopUp vocabsOfDay={vocabsOfDayPopUp}
                          closeDayPopUp={closeDayPopUp}/>}
        </div>
    )
}
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


type Props = {
    vocabs: Vocab[]
}

export default function CalendarPage(props: Readonly<Props>) {
    const [vocabIdsOfYearMonth, setVocabIdsOfYearMonth] = useState<VocabIdsOfDate[]>([])
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])

    function getVocabIdsOfMonth() {
        axios.get("/api/calendar/current-month")
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getVocabIdsOfMonth()
    }, []);


    function goToNextYearMonth() {
        const currentMonth: string = vocabIdsOfYearMonth[0].date.substring(8, 9)
        const currentYear: string = vocabIdsOfYearMonth[0].date.substring(11, 14)
        const currentMonthNumber: number = parseInt(currentMonth)
        const currentYearNumber: number = parseInt(currentYear)
        const month: string = currentMonthNumber < 11 ? (currentMonthNumber + 1).toString() : "0"
        const year: string = currentMonthNumber < 11 ? currentYear : (currentYearNumber + 1).toString()
        axios.get(`/api/calendar?year=${year}&month=${month}`)
            .then(response => setVocabIdsOfYearMonth(response.data))
            .catch(error => console.error(error))
    }

    function openDayPopUpAndPassItVocabs(vocabIdsOfDate: VocabIdsOfDate): void {
        const ids: string[] = vocabIdsOfDate.vocabIds;
        const vocabs: Vocab[] = props.vocabs.filter(vocab => ids.includes(vocab._id))
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
            {vocabIdsOfYearMonth.map(vocabIdsOfWeek =>
                <CalendarWeek
                    vocabIdsOfWeek={vocabIdsOfWeek}
                    openDayPopUpAndPassItVocabs={openDayPopUpAndPassItVocabs}/>)}
            {vocabsOfDayPopUp.length > 0 &&
                <DayPopUp vocabsOfDay={vocabsOfDayPopUp}
                          closeDayPopUp={closeDayPopUp}/>}
        </div>
    )
}
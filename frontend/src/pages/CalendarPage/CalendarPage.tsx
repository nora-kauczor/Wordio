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
        let nextYearMonth: string = ""
        if (currentMonthNumber < 12) {
            const nextMonthNumber: number = currentMonthNumber + 1
            nextYearMonth = nextMonthNumber.toString()+"-"+currentYear
        } else {
            const nextYearNumber: number = parseInt(currentYear) + 1
            nextYearMonth = `01-` + nextYearNumber.toString()
        }
        axios.get(`/api/calendar?year=${2024}&month=${10}`)
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
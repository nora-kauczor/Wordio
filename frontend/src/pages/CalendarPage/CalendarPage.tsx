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
import { uid } from 'uid';
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;

type Props = {
    vocabs: Vocab[]
}

export default function CalendarPage(props: Readonly<Props>) {
    const [vocabIdsOfYearMonth, setVocabIdsOfYearMonth] = useState<VocabIdsOfDate[]>([])
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])

    useEffect(() => {
        const today = new Date();
        const year = today.getFullYear().toString();
        const month = today.getMonth().toString();
        const vocabIds:VocabIdsOfDate[] = getVocabIdsOfYearMonth(month, year)
        setVocabIdsOfYearMonth(vocabIds)
    }, []);

    async function getVocabIdsOfYearMonth(month: string, year: string): VocabIdsOfDate[] {
        try {
            const response = await axios.get(`/api/calendar?year=${year}&month=${month}`)
            return response.data
        } catch {
            console.error(error)
            return [];
        }
    }

    function goToNextYearMonth() {
        const currentMonth: string = vocabIdsOfYearMonth[0].date.substring(8, 9)
        const currentYear: string = vocabIdsOfYearMonth[0].date.substring(11, 14)
        const currentMonthNumber: number = parseInt(currentMonth)
        const currentYearNumber: number = parseInt(currentYear)
        const month: string = currentMonthNumber < 11 ? (currentMonthNumber + 1).toString() : "0"
        const year: string = currentMonthNumber < 11 ? currentYear : (currentYearNumber + 1).toString()
        const vocabIds:VocabIdsOfDate[] = getVocabIdsOfYearMonth(month, year)
        setVocabIdsOfYearMonth(vocabIds)
    }

    function openDayPopUpAndPassItVocabs(vocabIdsOfDate: VocabIdsOfDate): void {
        const ids: string[] = vocabIdsOfDate.vocabIds;
        const vocabs: Vocab[] = props.vocabs.filter(vocab => vocab._id && ids.includes(vocab._id))
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
                    key={uid()}
                    vocabIdsOfWeek={vocabIdsOfWeek}
                    openDayPopUpAndPassItVocabs={openDayPopUpAndPassItVocabs}/>)}
            {vocabsOfDayPopUp.length > 0 &&
                <DayPopUp vocabsOfDay={vocabsOfDayPopUp}
                          closeDayPopUp={closeDayPopUp}/>}
        </div>
    )
}
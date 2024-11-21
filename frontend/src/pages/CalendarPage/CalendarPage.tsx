import './CalendarPage.css'
import React, {useEffect, useState} from "react";
import axios from "axios";
import CalendarWeek from "../../components/CalendarWeek/CalendarWeek.tsx";
import {
    VocabIdsOfDate
} from "../../types/VocabsIdsOfDate.ts";
import {Vocab} from "../../types/Vocab.ts";
import DayPopUp from "../../components/DayPopUp/DayPopUp.tsx";
import {uid} from 'uid';
import {
    Month,
} from "../../types/Month.ts";
import {useNavigate} from "react-router-dom";


type Props = {
    vocabs: Vocab[]
    language: string
    userId: string
    deactivateVocab: (id: string) => void
    deleteVocab: (id: string) => void
    openForm: (id: string) => void
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>
    setDisplayNewVocabsPopUp: React.Dispatch<React.SetStateAction<boolean>>

}

export default function CalendarPage(props: Readonly<Props>) {
    const [month, setMonth] = useState<Month>({
        vocabIdsOfMonth: [], yearMonthName: ""
    })
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])
    const [dayOfDayPopUp, setDayOfDayPopUp] = useState<string>("")
    const navigate = useNavigate()

    useEffect(() => {
        props.setUseForm(false)
        if (!props.language) {
            return
        }
        if (isVocabIdsOfMonthEmpty(month.vocabIdsOfMonth)) {
            getCurrentMonth()
        } else {
            updateDisplayedMonth()
        }
    }, [props.language, props.vocabs]);

    useEffect(() => {
        updateDayPopUp()
    }, [month]);

    function isVocabIdsOfMonthEmpty(vocabIdsOfMonth: VocabIdsOfDate[][]): boolean {
        return vocabIdsOfMonth.every(innerArray => !innerArray || innerArray.length === 0);
    }

    function getCurrentMonth(): void {
        const today = new Date();
        const year = today.getFullYear().toString();
        const month = (today.getMonth() + 1).toString();
        getMonth(month, year)
    }

    function updateDisplayedMonth(): void {
        if (isVocabIdsOfMonthEmpty(month.vocabIdsOfMonth)) {
            return
        }
        const someDate: string = month?.vocabIdsOfMonth[2][2].date
        const displayedYear = someDate.substring(0, 4)
        const displayedMonth = someDate.substring(5, 7)
        getMonth(displayedMonth, displayedYear)
    }

    function changeMonth(clickedButton: string): void {
        if (!month) {
            return
        }
        const currentYear: string | undefined = month.vocabIdsOfMonth[1][0].date.substring(
            0, 4)
        const currentMonth: string | undefined = month.vocabIdsOfMonth[1][0].date.substring(
            5, 9)
        const currentMonthNumber: number = parseInt(currentMonth)
        const currentYearNumber: number = parseInt(currentYear)
        let newMonth: number = 0
        let newYear: number = 0
        if (clickedButton === "previous") {
            newMonth = currentMonthNumber > 1 ? (currentMonthNumber - 1) : 12
            newYear = currentMonthNumber > 1 ? currentYearNumber :
                (currentYearNumber - 1)
        } else {
            newMonth = currentMonthNumber < 12 ? (currentMonthNumber + 1) : 1
            newYear = currentMonthNumber < 12 ? currentYearNumber :
                (currentYearNumber + 1)
        }
        getMonth(newMonth.toString(), newYear.toString())
    }

    function getMonth(month: string, year: string): void {
        axios.get(
            `/api/calendar?year=${year}&month=${month}&language=${props.language}`)
            .then(response => setMonth(response.data))
            .catch(error => console.error(error))
    }

    function openDayPopUpAndPassItVocabs(vocabIdsOfDate: VocabIdsOfDate): void {
        if (!vocabIdsOfDate.date) {
            return
        }
        setDayOfDayPopUp(vocabIdsOfDate.date)
        const vocabs: Vocab[] = getVocabsByIds(vocabIdsOfDate.vocabIds)
        setVocabsOfDayPopUp(vocabs)
    }

    function updateDayPopUp(): void {
        const ids: string[] | undefined = getIdsByDate(dayOfDayPopUp);
        if (!ids) {
            closeDayPopUp();
            return
        }
        const vocabs: Vocab[] = getVocabsByIds(ids)
        setVocabsOfDayPopUp(vocabs)
    }

    function getIdsByDate(date: string): string[] | undefined {
        if (!month.vocabIdsOfMonth) {
            return
        }
        for (let y: number = 0; y < 5; y++) {
            let weekWithoutNull = [];
            if (!month.vocabIdsOfMonth[y]) {
                continue
            } else {
                weekWithoutNull =
                    month.vocabIdsOfMonth[y].filter(day => day != null)
            }
            for (let z: number = 0; z < 8; z++) {
                if (weekWithoutNull[z]?.date === date) {
                    return weekWithoutNull[z]?.vocabIds
                }
            }
        }
    }

    function getVocabsByIds(ids: string[]): Vocab[] {
        return props.vocabs.filter(vocab => vocab.id && ids?.includes(vocab.id))
    }

    function closeDayPopUp(): void {
        setVocabsOfDayPopUp([])
        setDayOfDayPopUp("")
    }

    function handleClick(){
        navigate("/")
        props.setDisplayNewVocabsPopUp(false)
    }

    if (!month.yearMonthName) {
        return <p className={"loading-message"}>Loading...</p>
    }

    return (<div id={"calendar-page"} className={"page"} role={"main"}>
        <div style={{height: "30px"}}/>
        <div id={"button-and-header-container"}>
            {month && <button
                className={"calendar-button"}
                onClick={() => changeMonth("previous")}
                aria-label={"Previous month"}>◀︎
            </button>}
            {month && <h2>{month.yearMonthName}</h2>}
            {month && <button className={"calendar-button"}
                              onClick={() => changeMonth("next")}
                              aria-label={"Next month"}>▶︎
            </button>}
        </div>
        <article id={"weeks-wrapper"}>
            {month && month.vocabIdsOfMonth.map(vocabIdsOfWeek => <CalendarWeek
                key={uid()}
                vocabIdsOfWeek={vocabIdsOfWeek}
                openDayPopUpAndPassItVocabs={openDayPopUpAndPassItVocabs}/>)}
        </article>
        {vocabsOfDayPopUp.length > 0 && <div className={"overlay"}/>}
        {vocabsOfDayPopUp.length > 0 && <DayPopUp
            day={dayOfDayPopUp}
            vocabsOfDay={vocabsOfDayPopUp}
            closeDayPopUp={closeDayPopUp}
            openForm={props.openForm}
            deactivateVocab={props.deactivateVocab}
            deleteVocab={props.deleteVocab}
            userId={props.userId}/>}
        <button onClick={handleClick}
                aria-label={"Go back to the homepage"}
                className={"home-button"}>Home
        </button>
        <div style={{height: "10px"}}/>
    </div>)
}
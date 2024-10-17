import './CalendarPage.css'
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import CalendarDay
    from "../../components/CalendarDay/CalendarDay.tsx";
import {
    getDateAsString
} from "../../utils/getDateAsString.ts";

type Props = {
    vocabs: Vocab[]
}


export default function CalendarPage(props: Readonly<Props>) {
    const [days, setDays] = useState<string[]>([])

    function generateDays(): string[] {
        const today = new Date()
        const currentMonth = today.getMonth()
        const currentYear = today.getFullYear()
        let date: Date = new Date(currentYear, currentMonth, 1);
        let days: Date[] = []
        while (date.getMonth() === currentMonth) {
            days.push(new Date(date));
            date.setDate(date.getDate() + 1)
        }
        return  days.map(day => getDateAsString(day))
    }

    useEffect(() => {
        setDays(generateDays());
    }, []);

    return (
        <div id={"calendar-page"}>
            {days.map(day => <CalendarDay day={day}/>)}
        </div>
    )
}
import './CalendarPage.css'
import {Vocab} from "../../types/Vocab.ts";
import {useEffect, useState} from "react";
import CalendarDay
    from "../../components/CalendarDay/CalendarDay.tsx";

type Props = {
    vocabs: Vocab[]
}


export default function CalendarPage(props: Readonly<Props>) {
    const [days, setDays] = useState<Date[]>([])


    useEffect(() => {

    }, []);

    return (
        <div id={"calendar-page"}>
            {days.map(day => <CalendarDay day={day}/>)}
        </div>
    )
}
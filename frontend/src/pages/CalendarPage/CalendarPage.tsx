import './CalendarPage.css'
import {Vocab} from "../../types/Vocab.ts";
import {useState} from "react";

type Props = {
    vocabs:Vocab[]
}




export default function CalendarPage(props:Readonly<Props>){
const [month, setMonth] = useState()
    function getDaysInMonth(year, month) {
        // month is 0-indexed in JavaScript Date (0 = January, 11 = December)
        let date = new Date(year, month, 1);
        let days = [];

        // Loop until the month changes
        while (date.getMonth() === month) {
            // Push the current date to the array
            days.push(new Date(date));
            // Move to the next day
            date.setDate(date.getDate() + 1);
        }

        return days;
    }

// Example usage
    let year = 2024;
    let month = 9; // October (0-indexed: 9 = October)

    let daysInMonth = getDaysInMonth(year, month);
    daysInMonth.forEach(day => console.log(day.toDateString()));

    return(
        <div id={"calendar-page"}>
            {daysInMonth.map(day => <CalendarDay day={day} />)}
        </div>
    )
}
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
    const [vocabIdsOfMonth, setVocabIdsOfMonth] = useState([])
    const [vocabsOfDayPopUp, setVocabsOfDayPopUp] = useState<Vocab[]>([])

function getVocabIdsOfMonth() {
        axios.get("/api/calendar/current-month")
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
getVocabIdsOfMonth()
    }, []);

 // function goToNextMonth() {
 //     string nextMonth = vocabIdsOfMonth[0].date
 //     axios.get(`/api/calendar/${nextMonth}`)
 //         .then(response => console.log(response.data))
 //         .catch(error => console.error(error))
 // }

    function openDayPopUpAndPassItVocabs(vocabIdsOfDate: VocabIdsOfDate){
        const ids:string[] = vocabIdsOfDate.vocabIds;
        const vocabs:Vocab[] = props.vocabs.filter(vocab => ids.find(vocab._id))
        setVocabsOfDayPopUp(vocabs)

    }

    function closeDayPopUp(){
        setVocabsOfDayPopUp([])
    }


    return (
        <div id={"calendar-page"}>
            {vocabIdsOfMonth.map(vocabIdsOfWeek => <CalendarWeek vocabIdsOfWeek={vocabIdsOfWeek}/>)}
            {vocabsOfDayPopUp.length > 0 && <DayPopUp vocabsOfDay={vocabsOfDayPopUp}/>}
        </div>
    )
}
import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../VocabList/VocabList.tsx";
import './DayPopUp.css'


type Props = {
    day: string
    vocabsOfDay: Vocab[]
    closeDayPopUp: () => void
    deactivateVocab: (id: string) => void
    openForm: (id: string) => void
    userName:string
}

export default function DayPopUp(props: Readonly<Props>) {
    const displayDate: string = props.day.substring(8, 10) + "." +
        props.day.substring(5, 7) + "." + props.day.substring(0, 4)


    return (<div id={"day-popup"} className={"pop-up"} role={"dialog"}
                 aria-labelledby={"popup-title"} aria-modal={"true"}>
        <button onClick={props.closeDayPopUp}
                className={"close-button"}
                aria-label={"Close popup"}>✕
        </button>
        <h3>{displayDate}</h3>
        <VocabList vocabs={props.vocabsOfDay}
                   calendarMode={true}
                   deactivateVocab={props.deactivateVocab}
                   closeDayPopUp={props.closeDayPopUp}
                   openForm={props.openForm}
                   userName={props.userName}/>
    </div>)


}

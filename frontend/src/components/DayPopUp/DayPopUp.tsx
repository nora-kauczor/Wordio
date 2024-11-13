import {Vocab} from "../../types/Vocab.ts";
import VocabList from "../VocabList/VocabList.tsx";
import './DayPopUp.css'


type Props = {
    day: string
    vocabsOfDay: Vocab[]
    userId:string
    closeDayPopUp: () => void
    openForm: (id: string) => void
    deactivateVocab: (id: string) => void
    deleteVocab:(id: string) => void
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
        <h3 id={"date"}>{displayDate}</h3>
        <VocabList vocabs={props.vocabsOfDay}
                   calendarMode={true}
                   deactivateVocab={props.deactivateVocab}
                   deleteVocab={props.deleteVocab}
                   closeDayPopUp={props.closeDayPopUp}
                   openForm={props.openForm}
                   userId={props.userId}/>
    </div>)

}

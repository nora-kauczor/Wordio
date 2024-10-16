import './ReviewPage.css'
import axios from "axios";
import {useEffect, useState} from "react";
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";


export default function ReviewPage(){
const [todaysVocabs, setTodaysVocabs] = useState([]);
const [currentVocab, setCurrentVocab] = useState<Vocab>()
    function getTodaysVocabs():void{
        axios.get(`api/vocab/today`)
            .then(response => setTodaysVocabs(response.data))
            // .then(response => setTodaysVocabs(response.data[0]))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getTodaysVocabs()
    }, []);




    return(
    <div id={"review-page"}>
        {currentVocab && <CardContainer vocab={currentVocab}/>}
        <label htmlFor={"review-input"}></label>
        <input id={"review-input"}/>
    </div>
)}
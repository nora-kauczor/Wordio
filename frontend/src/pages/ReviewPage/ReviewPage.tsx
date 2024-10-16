import './ReviewPage.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {Vocab} from "../../types/Vocab.ts";




export default function ReviewPage(){
const [todaysVocabs, setTodaysVocabs] = useState([]);

    const vocabWithTodaysDate: Vocab = {
        _id: '670bc0ba64630f6a589cd2bf',
        word: 'hola',
        translation: 'hello',
        info: 'added text',
        language: 'Spanish',
        reviewDates: ["16-10-2024"]
    }

    function getTodaysVocabs():void{
        axios.get(`api/vocab/today`)
            .then(response => setTodaysVocabs(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getTodaysVocabs()
    }, []);

    return(
    <div id={"review-page"}>

    </div>
)}
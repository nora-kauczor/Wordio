import './ReviewPage.css'
import axios from "axios";
import {useEffect, useState} from "react";


export default function ReviewPage(){
const [todaysVocabs, setTodaysVocabs] = useState([]);

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
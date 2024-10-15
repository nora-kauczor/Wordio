import './CalendarPage.css'
import axios from "axios";
import {useEffect, useState} from "react";



export default function CalendarPage( ){
    const [vocabs, setVocabs] = useState([])

    function getAllVocabs() {
        axios.get("/api/vocab")
            .then(response => setVocabs(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getAllVocabs()
    }, []);

    return(
        <div id={"calendar-page"}>

        </div>
    )
}
import './App.css'
import {
    Route,
    Routes
} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage
    from "./pages/CalendarPage/CalendarPage.tsx";
import axios from "axios";
import {useEffect, useState} from "react";
import Form from "./components/Form/Form.tsx";
import {Vocab} from "./types/Vocab.ts";
import BacklogPage
    from "./pages/BacklogPage/BacklogPage.tsx";
import NavBar from "./components/NavBar/NavBar.tsx";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)

    function getAllVocabs() {
        axios.get("/api/vocab")
            .then(response => setVocabs(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getAllVocabs()
    }, []);

    function getVocab(_id: string): void {
        axios.get(`api/vocab/${_id}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }


    function deleteVocab(_id: string): void {
        axios.delete(`api/vocab/${_id}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    function editVocab(editedVocab: Vocab): void {
        axios.put(`api/vocab/${editedVocab._id}`, editedVocab)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    console.log(vocabs)
    return (
        <div id={"app"}>
            {useForm && <Form/>}
            <NavBar setUseForm={setUseForm}/>
            <Routes>
                <Route path={"/"}
                       element={<HomePage/>}></Route>
                {vocabs.length > 0 &&
                    <Route path={"/calendar"} element={
                        <CalendarPage
                            vocabs={vocabs}/>}></Route>}
                <Route path={"/review"}
                       element={<ReviewPage/>}></Route>
                {vocabs.length > 0 &&
                    <Route path={"/backlog"}
                           element={<BacklogPage
                               vocabs={vocabs.filter(vocab => vocab.reviewDates.length === 0)}
                               deleteVocab={deleteVocab}
                           />}></Route>}
            </Routes>


        </div>
    )
}

export default App

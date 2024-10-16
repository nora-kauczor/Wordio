import './App.css'
import {
    BrowserRouter,
    Route,
    Routes
} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage
    from "./pages/CalendarPage/CalendarPage.tsx";
import axios from "axios";
import {useEffect} from "react";
import Form from "./components/Form/Form.tsx";
import {Vocab} from "./types/Vocab.ts";

function App() {

    function getVocab(_id: string): void {
        axios.get(`api/vocab/${_id}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    const editedVocab: Vocab = {
        _id: '670bc0ba64630f6a589cd2bf',
        word: 'hola',
        translation: 'hello',
        info: 'newly added text',
        language: 'Spanish',
        reviewDates: []
    }

    useEffect(() => {
        editVocab(editedVocab)
        getVocab("670bc0ba64630f6a589cd2bf")
    }, [editedVocab]);

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



    return (
        <>
            <Form/>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"}
                           element={<HomePage/>}></Route>
                    <Route path={"/calendar"} element={
                        <CalendarPage/>}></Route>
                    <Route path={"/review"}
                           element={<ReviewPage/>}></Route>
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App

import './App.css'
import {
    Route,
    Routes, useNavigate
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
import LoginPage from "./pages/LoginPage/LoginPage.tsx";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userName, setUserName] = useState<string>("")

    function getAllVocabs() {
        axios.get("/api/vocab")
            .then(response => setVocabs(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getAllVocabs()
    }, []);


    function getTodaysVocabs(): Vocab[] {
        const date = new Date();
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const today = `${year}-${month}-${day}`;
        return vocabs.filter(vocab => vocab.reviewDates.includes(today))
    }

    function getVocab(_id: string): void {
        axios.get(`api/vocab/${_id}`)
            .then(response => console.log("fetched with getVocab:", response.data))
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

    function activateVocab(_id: string): void {
        axios.get(`api/vocab/activate/${_id}`)
            .then(() => console.log(`Vocab ${_id} successfully activated.`))
            .then(() => getVocab("670bc0ba64630f6a589cd2d4"))
            .catch(error => console.error(error))
    }

    const navigate = useNavigate();

    function logout (){
        setUserName("")
        const host = window.location.host ===
        'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/api/auth/logout', '_self')
    }


    useEffect(() => {
        axios.get("/api/vocab/auth")
            .then(response => setUserName(response.data.name))
            // .then(()=> navigate("/"))
            .catch(error => console.error(error))
    }, []);

    return (
        <div id={"app"}>
            {useForm && <Form/>}
            <NavBar setUseForm={setUseForm}/>
            {userName && <button onClick={logout}>logout</button>}
            {userName && <p>Your are logged in as {userName}</p>}
            <Routes>
                <Route path={"/login"}
                       element={<LoginPage setUserName={setUserName}/>}></Route>
                <Route path={"/"}
                       element={<HomePage finishedReviewing={vocabsLeftToReview.length > 1 ? false : true}/>}></Route>
                    <Route path={"/calendar"} element={
                        <CalendarPage vocabs={vocabs}/>}></Route>
                <Route path={"/review"}
                       element={<ReviewPage
                           todaysVocabs={getTodaysVocabs()}/>}></Route>
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

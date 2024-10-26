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
import Header from "./components/Header/Header.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import useLocalStorageState from "use-local-storage-state";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userName, setUserName] = useState<string>("")
    const [language, setLanguage] = useLocalStorageState("language", {defaultValue: ""});

    function changeLanguage(){

    }

    function getAllVocabsOfLanguage() {
        axios.get(`/api/vocab/language?language=${language}`)
            .then(response => setVocabs(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getAllVocabsOfLanguage()
        getUserName()
    }, []);


    function getTodaysVocabs(): Vocab[] {
        const date: Date = new Date()
        const year: number = date.getFullYear()
        const month: string = String(date.getMonth() + 1).padStart(2, '0')
        const day: string = String(date.getDate()).padStart(2, '0')
        const today: string = `${year}-${month}-${day}`
        const allOfTodaysVocabs: Vocab[] = vocabs
            .filter(vocab => vocab.reviewDates.includes(today))
        return allOfTodaysVocabs.filter(vocab => vocab.language === language)
    }


    function deleteVocab(_id: string): void {
        axios.delete(`api/vocab/${_id}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    // function editVocab(editedVocab: Vocab): void {
    //     axios.put(`api/vocab/${editedVocab._id}`, editedVocab)
    //         .then(response => console.log(response.data))
    //         .catch(error => console.error(error))
    // }
    //
    // function activateVocab(_id: string): void {
    //     axios.get(`api/vocab/activate/${_id}`)
    //         .then(() => console.log(`Vocab ${_id} successfully activated.`))
    //         .then(() => getVocab("670bc0ba64630f6a589cd2d4"))
    //         .catch(error => console.error(error))
    // }

    const navigate = useNavigate();

    function logout() {
        setUserName("")
        const host = window.location.host ===
        'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/api/auth/logout', '_self')
    }


    function getUserName(): void {
        axios.get("/api/vocab/auth")
            .then(response => setUserName(response.data.name))
            .catch(error => console.error(error))
    }


    useEffect(() => {
        if (userName) {
            navigate("/")
        }
    }, [userName]);


    return (
        <div id={"app"}>

            <Header userName={userName} logout={logout}/>
            <label htmlFor={"select-language"}>Change language</label>
            <select id={"select-language"}>
                <option>🇪🇸 Spanish</option>
                <option>🇫🇷 French</option>
                <option>🇮🇹 Italian</option>
            </select>
            {useForm && <Form/>}
            <NavBar setUseForm={setUseForm}/>
            <Routes>
                <Route path={"/login"}
                       element={<LoginPage
                       />}/>
                <Route element={<ProtectedRoutes
                    userName={userName}/>}>
                    <Route path={"/"}
                           element={<HomePage
                               // finishedReviewing={vocabsLeftToReview.length > 1 ? false : true}
                               finishedReviewing={false}
                               setUseForm={setUseForm}/>}/>
                    <Route path={"/calendar"} element={
                        <CalendarPage
                            vocabs={vocabs}/>}/>
                    <Route path={"/review"}
                           element={<ReviewPage
                               // todaysVocabs={getTodaysVocabs()}
                           />}/>
                    <Route path={"/backlog"}
                           element={<BacklogPage
                               vocabs={vocabs.filter(vocab => vocab.reviewDates.length === 0)}
                               deleteVocab={deleteVocab}
                           />}/>

                </Route>
            </Routes>
        </div>
    )
}

export default App

import './App.css'
import {
    Route, Routes, useNavigate
} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage from "./pages/CalendarPage/CalendarPage.tsx";
import axios from "axios";
import {useEffect, useState} from "react";
import Form from "./components/Form/Form.tsx";
import {Vocab} from "./types/Vocab.ts";
import BacklogPage from "./pages/BacklogPage/BacklogPage.tsx";
import NavBar from "./components/NavBar/NavBar.tsx";
import LoginPage from "./pages/LoginPage/LoginPage.tsx";
import Header from "./components/Header/Header.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import useLocalStorageState from "use-local-storage-state";
import DisplayPage from "./pages/DisplayPage/DisplayPage.tsx";
import { toast } from "react-toastify";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userName, setUserName] = useState<string>("")
    const [language, setLanguage] = useLocalStorageState("language",
        {defaultValue: ""});
    const [vocabsLeftToReview, setVocabsLeftToReview] = useLocalStorageState<Vocab[]>(
        "vocabsLeftToReview", {defaultValue: []})
    const [todaysVocabs, setTodaysVocabs] = useLocalStorageState<Vocab[]>(
        "todaysVocabs", {defaultValue: []})
    const [vocabToEdit, setVocabToEdit] = useState<Vocab | undefined>(undefined)
    const [displayNewVocabsPopUp, setDisplayNewVocabsPopUp] = useState(false)
    const navigate = useNavigate()

    function getAllVocabsOfLanguage() {
        axios.get(`/api/vocab?language=${language}`)
            .then(response => setVocabs(response.data))
            .then(() => updateVocabsLeftToReview())
            .catch(error => console.error(error))
    }


    function getUserName(): void {
        axios.get("/api/vocab/auth")
            .then(response => setUserName(response.data.name))
            .then(() => navigate("/"))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getUserName()
        if (language) {
            getAllVocabsOfLanguage()
        }
    }, []);

    useEffect(() => {
        if (userName) {
            navigate("/")
        }
    }, [userName]);

    useEffect(() => {
        getAllVocabsOfLanguage()
    }, [language]);


    function updateVocabsLeftToReview(): void {
        const updatedTodaysVocabs: Vocab[] = getTodaysVocabs()
        const vocabsToReviewWithoutDeletedOnes: Vocab[] = vocabsLeftToReview
            .filter((vocabToReview: Vocab) => updatedTodaysVocabs
                .find(vocabFromTodays => vocabFromTodays._id ===
                    vocabToReview._id))
        const newVocabs: Vocab[] = updatedTodaysVocabs
            .filter(vocabFromUpdatedOnes => todaysVocabs
                .find((vocabFromOldOnes: Vocab) => vocabFromOldOnes._id !=
                    vocabFromUpdatedOnes._id))
        const updatedVocabsToReview: Vocab[] = [...vocabsToReviewWithoutDeletedOnes,
            ...newVocabs]
        setVocabsLeftToReview(updatedVocabsToReview)
        setTodaysVocabs(updatedTodaysVocabs)
    }

    function removeVocabFromVocabsToReview(_id: string | null): void {
        setVocabsLeftToReview(
            vocabsLeftToReview.filter((vocab: Vocab) => vocab._id === _id))
    }

    function getTodaysVocabs(): Vocab[] {
        const date: Date = new Date()
        const year: number = date.getFullYear()
        const month: string = String(date.getMonth() + 1).padStart(2, '0')
        const day: string = String(date.getDate()).padStart(2, '0')
        const today: string = `${year}-${month}-${day}`
        const allOfTodaysVocabs: Vocab[] = vocabs
            .filter(vocab => vocab.datesPerUser?.userName.includes(today))
        return allOfTodaysVocabs.filter(vocab => vocab.language === language)
    }

    function activateVocab(_id: string): void {
        axios.put(`api/vocab/activate/${_id}`)
            .then(() => {
                console.log(`Vocab ${_id} successfully activated.`)
                toast.success("Vocab successfully activated.")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't activate vocab")})
    }

    function deactivateVocab(_id: string): void {
        axios.put(`api/vocab/deactivate/${_id}`)
            .then(() => {
                console.log(`Vocab ${_id} successfully deactivated.`)
                toast.success("Vocab successfully deactivated.")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't deactivate vocab")})
    }

    function changeReviewDates(_id: string | null): void {
        axios.put(`api/vocab/change-dates/${_id}`)
            .then(() => {
                console.log(`Vocab ${_id}'s review dates successfully deactivated.`)
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Something went wrong.")})
    }

    function logout() {
        setUserName("")
        const host = window.location.host === 'localhost:5173' ?
            'http://localhost:8080' : window.location.origin
        window.open(host + '/api/auth/logout', '_self')
    }


    function deleteVocab(_id: string): void {
        axios.delete(`api/vocab/${_id}`)
            .then(() => {
                console.log(`Vocab ${_id} successfully deleted.`)
                toast.success("Vocab successfully deleted")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't delete vocab")})
    }

    function createVocab(newVocab: Vocab): void {
        setUseForm(false)
        axios.post("/api/vocab", newVocab)
            .then(() => {
                console.log(`New vocab successfully created`)
                toast.success("New vocab successfully created")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't create new vocab")})
    }

    function createAndActivateVocab(newVocab: Vocab): void {
        setUseForm(false)
        axios.post("/api/vocab/activate", newVocab)
            .then(response => {
                navigate(`/display/:${response.data._id}`)
                console.log("New vocab was successfully created and activated.")
                toast.success("New vocab successfully created and activated")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't create and activate new vocab")})
    }

    function editVocab(editedVocab: Vocab): void {
        setVocabToEdit(undefined)
        setUseForm(false)
        axios.put(`api/vocab/`, editedVocab)
            .then(() => {
                console.log(`Vocab ${editedVocab._id} successfully edited.`)
                toast.success("Vocab successfully edited")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't edit vocab")})
    }


    function openForm(_id: string | undefined) {
        if (displayNewVocabsPopUp) {
            setDisplayNewVocabsPopUp(false)
        }
        setUseForm(true)
        if (!_id) {
            const vocab = vocabs.find(vocab => vocab._id === _id)
            setVocabToEdit(vocab)
        }
    }

    return (<div id={"app"} role={"main"}>
        <Header userName={userName} logout={logout}
                language={language}
                setLanguage={setLanguage}/>
        <div style={{height: "50px"}}/>
        {useForm && <div className={"overlay"}/>}
        {useForm && <Form userName={userName} language={language}
                          editVocab={editVocab} createVocab={createVocab}
                          createAndActivateVocab={createAndActivateVocab}
                          vocabToEdit={vocabToEdit} setUseForm={setUseForm}/>}
        <NavBar setUseForm={setUseForm}/>
        <Routes>
            <Route path={"/login"}
                   element={<LoginPage
                   />}/>
            <Route element={<ProtectedRoutes
                userName={userName}/>}>
                <Route path={"/"}
                       element={<HomePage
                           userName={userName}
                           vocabs={vocabs}
                           finishedReviewing={vocabsLeftToReview.length < 1}
                           setUseForm={setUseForm}
                           language={language}
                           setLanguage={setLanguage}
                           displayNewVocabsPopUp={displayNewVocabsPopUp}
                           setDisplayNewVocabsPopUp={setDisplayNewVocabsPopUp}/>}/>
                <Route path={"/calendar"} element={<CalendarPage
                    userName={userName}
                    setUseForm={setUseForm}
                    openForm={openForm}
                    vocabs={vocabs}
                    language={language}
                    deactivateVocab={deactivateVocab}/>}/>
                <Route path={"/review"}
                       element={<ReviewPage
                           removeVocabFromVocabsToReview={removeVocabFromVocabsToReview}
                           vocabsLeftToReview={vocabsLeftToReview}
                           changeReviewDates={changeReviewDates}/>}/>
                <Route path={"/backlog"}
                       element={<BacklogPage
                           vocabs={vocabs.filter(vocab => vocab.datesPerUser &&
                               Object.keys(vocab.datesPerUser).length !== 0 ||
                               !vocab.datesPerUser?.userName)}
                           deleteVocab={deleteVocab}
                           activateVocab={activateVocab}
                           language={language}
                           openForm={openForm}
                           setUseForm={setUseForm}
                           userName={userName}
                       />}/>
                <Route path={"/display/:_id"}
                       element={<DisplayPage
                           vocabs={vocabs}
                       />}/>
            </Route>
        </Routes>
        <div style={{height: "60px"}}/>
    </div>)
}

export default App
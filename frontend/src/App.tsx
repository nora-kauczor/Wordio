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
import {toast, ToastContainer} from "react-toastify";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userId, setUserId] = useState<string>("")
    const [language, setLanguage] = useLocalStorageState<string>("language",
        {defaultValue: ""})
    const [vocabsToReview, setVocabsToReview] = useState<Vocab[]>([])
    const [vocabToEdit, setVocabToEdit] = useState<Vocab | undefined>(undefined)
    const [displayNewVocabsPopUp, setDisplayNewVocabsPopUp] = useState(false)
    const navigate = useNavigate()
    console.log("vocabs[4], el libro: ", vocabs[4])
console.log("vocabsToReview: ", vocabsToReview)
    useEffect(() => {
        getUserId()
    }, []);

    useEffect(() => {
        if (userId) {
            navigate("/")
        } else {
            navigate("/login")
        }
    }, [userId]);

    useEffect(() => {
        if (language && userId) {
            getAllVocabsOfLanguage()
        }
    }, [language, userId]);

    useEffect(() => {
        if (vocabs.length > 0) {
            getVocabsToReview()
        }
    }, [vocabs]);


    function getAllVocabsOfLanguage() {
        if (language && userId) {
            axios.get(`/api/vocab?language=${language}`)
                .then(response => {
                    setVocabs(response.data)
                })
                .catch(error => console.error(error))
        }
    }

    function getUserId(): void {
        axios.get("/api/vocab/auth")
            .then(response => {
                setUserId(String(response.data))
            })
            .catch(error => console.error(error))
    }

    function logout() {
        setUserId("")
        const host = window.location.host === 'localhost:5173' ?
            'http://localhost:8080' : window.location.origin
        window.open(host + '/api/auth/logout', '_self')
    }

    function getVocabById(id: string): Vocab | void {
        if (vocabs.length < 1) {
            console.error("Couldn't get Vocab by ID because vocabs was empty.");
        } else {
            return vocabs.find(vocab => vocab.id === id)
        }
    }

    function getVocabsToReview() {
        console.log("getVocabsToReview was called.")
        // wird erst gecalled, nachdem vocabs schon geändert ist, also
        axios.get(`/api/review?language=${language}`)
            .then(response => {
                const idsOfNonReviewedVocabs: string[] = Object.entries(
                    response.data.idsOfVocabsToReview)
                    .filter(innerArray => innerArray[1] === false)
                    .map(innerArray => innerArray[0]);
                console.log("idsOfNonReviewedVocabs: ", idsOfNonReviewedVocabs)
                if (vocabs.length < 1) {
                    console.error(
                        "Couldn't get vocabs to review because vocabs was empty.");
                } else {
                    const vocabsToReview: (Vocab | void)[] = idsOfNonReviewedVocabs
                        .map(id => {
                            return getVocabById(id)
                        })
                        .filter((vocab): vocab is Vocab => vocab!== undefined)
                setVocabsToReview(vocabsToReview as Vocab[])
                }
            })
            .catch(error => {
                console.error(error)
            })
    }

    function removeVocabFromVocabsToReview(id: string): void {
        axios.put(`/api/review/${id}`)
            .then(() => {
                console.log(`Vocab ${id} was marked as reviewed for today.`)
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
            })
    }

    function activateVocab(id: string): void {
        axios.put(`api/vocab/activate/${id}`)
            .then(() => {
                console.log(`Vocab ${id} successfully activated.`)
                toast.success("Vocab successfully activated.")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't activate vocab")
            })
    }

    function deactivateVocab(id: string): void {
        axios.put(`api/vocab/deactivate/${id}`)
            .then(() => {
                console.log(`Vocab ${id} successfully deactivated.`)
                toast.success("Vocab successfully deactivated.")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't deactivate vocab")
            })
    }

    function changeReviewDates(id: string | null): void {
        axios.put(`api/vocab/change-dates/${id}`)
            .then(() => {
                console.log(`Vocab ${id}'s review dates successfully updated.`)
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Something went wrong.")
            })
    }

    function deleteVocab(id: string): void {
        axios.delete(`api/vocab/${id}`)
            .then(() => {
                console.log(`Vocab ${id} successfully deleted.`)
                toast.success("Vocab successfully deleted")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't delete vocab")
            })
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
                toast.error("Couldn't create new vocab")
            })
    }

    function createAndActivateVocab(newVocab: Vocab): void {
        setUseForm(false)
        axios.post("/api/vocab/activate", newVocab)
            .then(response => {
                navigate(`/display/:${response.data.id}`)
                console.log("New vocab was successfully created and activated.")
                toast.success("New vocab successfully created and activated")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't create and activate new vocab")
            })
    }

    function editVocab(editedVocab: Vocab): void {
        setVocabToEdit(undefined)
        setUseForm(false)
        axios.put(`api/vocab/`, editedVocab)
            .then(() => {
                console.log(`Vocab ${editedVocab.id} successfully edited.`)
                toast.success("Vocab successfully edited")
                getAllVocabsOfLanguage()
            })
            .catch(error => {
                console.error(error)
                toast.error("Couldn't edit vocab")
            })
    }

    function openForm(id: string | undefined) {
        if (displayNewVocabsPopUp) {
            setDisplayNewVocabsPopUp(false)
        }
        setUseForm(true)
        if (!id) {
            const vocab = vocabs.find(vocab => vocab.id === id)
            setVocabToEdit(vocab)
        }
    }

    function getInactiveVocabs() {
        return vocabs.filter(vocab => {
            if (!vocab.datesPerUser) {
                return true
            }
            if (!vocab.datesPerUser[userId]) {
                return true
            }
            if (vocab.datesPerUser[userId].length < 1) {
                return true
            }
        })
    }

    if (userId && language && vocabs.length < 1) {
        return <p className={"loading-message"}>Loading...</p>
    }

    return (<div id={"app"} role={"main"}>
        <ToastContainer autoClose={2000} hideProgressBar={true}
                        closeButton={false}/>
        <Header userId={userId} logout={logout}
                language={language}
                setLanguage={setLanguage}/>
        {useForm && <div className={"overlay"}/>}
        {useForm && <Form userId={userId} language={language}
                          editVocab={editVocab} createVocab={createVocab}
                          createAndActivateVocab={createAndActivateVocab}
                          vocabToEdit={vocabToEdit} setUseForm={setUseForm}/>}
        {userId && <NavBar useForm={useForm} setUseForm={setUseForm}/>}
        <Routes>
            <Route path={"/login"}
                   element={<LoginPage
                   />}/>
            <Route element={<ProtectedRoutes
                userId={userId}/>}>
                <Route path={"/"}
                       element={<HomePage
                           userId={userId}
                           vocabs={vocabs}
                           finishedReviewing={vocabsToReview.length < 1}
                           allVocabsActivated={getInactiveVocabs().length < 1}
                           setUseForm={setUseForm}
                           language={language}
                           setLanguage={setLanguage}
                           displayNewVocabsPopUp={displayNewVocabsPopUp}
                           setDisplayNewVocabsPopUp={setDisplayNewVocabsPopUp}
                           activateVocab={activateVocab}/>}/>
                <Route path={"/calendar"} element={<CalendarPage
                    userId={userId}
                    setUseForm={setUseForm}
                    openForm={openForm}
                    vocabs={vocabs}
                    language={language}
                    deactivateVocab={deactivateVocab}
                    deleteVocab={deleteVocab}/>}/>
                <Route path={"/review"}
                       element={<ReviewPage
                           language={language}
                           removeVocabFromVocabsToReview={removeVocabFromVocabsToReview}
                           vocabsToReview={vocabsToReview}
                           changeReviewDates={changeReviewDates}
                       />}/>
                <Route path={"/backlog"}
                       element={<BacklogPage
                           vocabs={getInactiveVocabs()}
                           allVocabsActivated={getInactiveVocabs().length < 1}
                           deleteVocab={deleteVocab}
                           activateVocab={activateVocab}
                           language={language}
                           openForm={openForm}
                           setUseForm={setUseForm}
                           userId={userId}
                       />}/>
                <Route path={"/display/:id"}
                       element={<DisplayPage
                           vocabs={vocabs}
                           setDisplayNewVocabsPopUp={setDisplayNewVocabsPopUp}

                       />}/>
            </Route>
        </Routes>
        <div style={{height: "30px"}}/>
    </div>)
}

export default App
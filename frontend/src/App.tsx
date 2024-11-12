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
import {ReviewDay} from "./types/ReviewDay.ts";

function App() {

    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userId, setUserId] = useState<string>("")
    const [language, setLanguage] = useLocalStorageState<string>("language",
        {defaultValue: ""})
    const [vocabsToReview, setVocabsToReview] = useLocalStorageState<Vocab[]>(
        "vocabsToReview", {defaultValue: []})
    const [vocabToEdit, setVocabToEdit] = useState<Vocab | undefined>(undefined)
    const [displayNewVocabsPopUp, setDisplayNewVocabsPopUp] = useState(false)
    const navigate = useNavigate()

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
        getVocabsToReview()}
    }, [vocabs]);


    function getAllVocabsOfLanguage() {
        if (!language || !userId) {
            return
        }
        axios.get(`/api/vocab?language=${language}`)
            .then(response => {
                setVocabs(response.data)
            })
            .catch(error => console.error(error))
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
        console.log(id)
        if (vocabs.length < 1) {
            console.error("Couldn't get Vocab by ID because vocabs was null.");
            return
        }
        return vocabs.find(vocab => vocab.id === id)
    }

    // console.log(getVocabById("67306f9c3b9e9f5d965a4a7b"))

    function getVocabsToReview() {
        axios.get(`/api/review?language=${language}`)
            .then(response => {
                const idsOfNonReviewedVocabs = Object.entries(
                    response.data.idsOfVocabsToReview)
                    .filter(([id, value]) => value === false)
                    .map(([id]) => id);

                console.log(idsOfNonReviewedVocabs)
                if (!vocabs) {
                    console.error(
                        "Couldn't get vocabs to review because vocabs was null.");
                    return
                }
                // console.log(typeof idsOfNonReviewedVocabs)
                // console.log(typeof vocabs)


                console.log(getVocabById(idsOfNonReviewedVocabs[0]))

                const vocabsToReview: Vocab[] = idsOfNonReviewedVocabs.map(
                    vocab => {
                        id => getVocabById(id)
                    })

                // const vocabsToReview: Vocab[] = vocabs.filter(vocab => {
                //     if (!vocab.id) {
                //         return false
                //     } else {
                //         idsOfNonReviewedVocabs.some(id => id == vocab.id)
                //     }
                // })
                // console.log(vocabsToReview)
                setVocabsToReview(vocabsToReview)
            })
            .catch(error => {
                console.error(error)
            })
    }

    // console.log(vocabsToReview)

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
                {/*{!vocabsToReviewUpdated && */}
                {/*    <Route path={"/"}*/}
                {/*                                  element={<p*/}
                {/*                                      className={"loading-message"}>Loading...</p>}/>*/}
                {/*}*/}
                {/*{vocabsToReviewUpdated && */}
                <Route path={"/"}
                       element={<HomePage
                           userId={userId}
                           vocabs={vocabs}
                           // finishedReviewing={vocabsToReview.length < 1}
                           allVocabsActivated={getInactiveVocabs().length < 1}
                           setUseForm={setUseForm}
                           language={language}
                           setLanguage={setLanguage}
                           displayNewVocabsPopUp={displayNewVocabsPopUp}
                           setDisplayNewVocabsPopUp={setDisplayNewVocabsPopUp}
                           activateVocab={activateVocab}/>}/>
                {/*}*/}
                <Route path={"/calendar"} element={<CalendarPage
                    userId={userId}
                    setUseForm={setUseForm}
                    openForm={openForm}
                    vocabs={vocabs}
                    language={language}
                    deactivateVocab={deactivateVocab}/>}/>
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
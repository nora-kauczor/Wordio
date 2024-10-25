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
import useLocalStorageState from "use-local-storage-state"
import Header from "./components/Header/Header.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";

function App() {
    const [vocabs, setVocabs] = useState<Vocab[]>([])
    const [useForm, setUseForm] = useState<boolean>(false)
    const [userName, setUserName] = useState<string>("")
    const [vocabsLeftToReview, setVocabsLeftToReview] = useLocalStorageState<Vocab[]>("vocabsLeftToReview", {defaultValue: []})
    const [todaysVocabs, setTodaysVocabs] = useLocalStorageState<Vocab[]>("todaysVocabs", {defaultValue: []})

    function getAllVocabs() {
        axios.get("/api/vocab")
            .then(response => setVocabs(response.data))
            .then(() => updateVocabsLeftToReview())
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getAllVocabs()
        getUserName()
    }, []);

    function updateVocabsLeftToReview():void {
        const updatedTodaysVocabs: Vocab[] = getTodaysVocabs()
        // delete vocabs that have been deleted from today's
        const vocabsToReviewWithoutDeletedOnes: Vocab[] = vocabsLeftToReview.filter(vocabToReview => updatedTodaysVocabs.find(vocabFromTodays => vocabFromTodays._id === vocabToReview._id))
        // get vocabs that are new
        const newVocabs: Vocab[] = updatedTodaysVocabs.filter(vocabFromUpdatedOnes => todaysVocabs.find(vocabFromOldOnes => vocabFromOldOnes._id != vocabFromUpdatedOnes._id))
        const updatedVocabsToReview: Vocab[] = [...vocabsToReviewWithoutDeletedOnes, ...newVocabs]
        setVocabsLeftToReview(updatedVocabsToReview)
        // only update today's vocabs after the above comparison
        setTodaysVocabs(updatedTodaysVocabs)
    }

    function removeVocabFromVocabsToReview(_id:string):void{
        setVocabsLeftToReview(vocabsLeftToReview.filter(vocab => vocab._id === _id))
    }

    function getTodaysVocabs(): Vocab[] {
        const date = new Date();
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const today = `${year}-${month}-${day}`;
        return vocabs.filter(vocab => vocab.reviewDates.includes(today))
    }

    // function getVocab(_id: string): void {
    //     axios.get(`api/vocab/${_id}`)
    //         .then(response => console.log("fetched with getVocab:", response.data))
    //         .catch(error => console.error(error))
    // }

    function deleteVocab(_id: string): void {
        axios.delete(`api/vocab/${_id}`)
            .then(() => console.log(`Vocab ${_id} successfully deleted.`))
            .then(() => getAllVocabs())
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
    //         .then(() => getAllVocabs())
    //         .catch(error => console.error(error))
    // }

    const navigate = useNavigate();

    function logout() {
        setUserName("")
        const host = window.location.host ===
        'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/api/auth/logout', '_self')
    }



    function getUserName():void{
        axios.get("/api/vocab/auth")
            .then(response => setUserName(response.data.name))
            .then(() => navigate("/"))
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
                        <CalendarPage vocabs={vocabs}/>}/>
                <Route path={"/review"}
                       element={<ReviewPage
                           removeVocabFromVocabsToReview={removeVocabFromVocabsToReview}
                           vocabsLeftToReview={vocabsLeftToReview}/>}/>

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

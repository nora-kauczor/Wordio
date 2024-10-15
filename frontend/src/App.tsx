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

function App() {

    function getVocab(_id:string):void{
        axios.get(`api/vocab/${_id}`)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        getVocab("670bc0ba64630f6a589cd2bf")
    }, []);

    return (
        <>
            <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<HomePage/>}></Route>
                <Route path={"/calendar"} element={<CalendarPage />}></Route>
                <Route path={"/review"} element={<ReviewPage/>}></Route>
            </Routes>
            </BrowserRouter>
        </>
    )
}

export default App

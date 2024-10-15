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

function App() {


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

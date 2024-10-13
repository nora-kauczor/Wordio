import './App.css'
import {Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage
    from "./pages/CalendarPage/CalendarPage.tsx";
import {useState} from "react";

function App() {
const [vocabs, setVocabs] = useState([])


  return (
    <>
      <Routes>
          <Route path={"/"} element={<HomePage/>}></Route>
          <Route path={"/calendar"} element={<CalendarPage vocabs={vocabs}/>}></Route>
          <Route path={"/review"} element={<ReviewPage vocabsOfToday={vocabs}/>}></Route>
      </Routes>
    </>
  )
}

export default App

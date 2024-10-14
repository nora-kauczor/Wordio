import './App.css'
import {Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage
    from "./pages/CalendarPage/CalendarPage.tsx";
import {useState} from "react";
import axios from "axios";

function App() {
const [vocabs, setVocabs] = useState([])
// function getVocabsOfToday: sollte man die request nicht eher aus der komponente stellen?
// auf alle fälle macht es mehr sinn, wenn der state in der komponente gespeichert wird (zumindest zum jetzigen stand)
    // d.h. ich lade das imme rjeweils nur, wenn diese seite aufgerufen wird
    // function getAllVocabs

    axios.get("/api/vocab")
        .then(response => console.log(response.data))
        .catch(error => console.error(error))

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

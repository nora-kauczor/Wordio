import './App.css'
import {Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage.tsx";
import ReviewPage from "./pages/ReviewPage/ReviewPage.tsx";
import CalendarPage
    from "./pages/CalendarPage/CalendarPage.tsx";

function App() {


  return (
    <>
      <Routes>
          <Route path={"/"} element={<HomePage/>}></Route>
          <Route path={"/calendar"} element={<CalendarPage vocabs={vocabs}/>}></Route>
          <Route path={"/review"} element={<ReviewPage vocabsOfTheDay={vocabsOfTheDay}/>}></Route>
      </Routes>
    </>
  )
}

export default App

import './Form.css'
import {Vocab} from "../../types/Vocab.ts";
import axios from "axios";


export default function Form() {
    function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const newVocab: Vocab = {
            id: null,
            word: event.target.word.value,
            translation: event.target.translation.value,
            info: event.target.info.value,
            language: "Spanish",
            reviewDates: []
        }
        createVocab(newVocab)
    }

    function createVocab(newVocab:Vocab):void {
        axios.post("/api/vocab", newVocab)
            .then(() => console.log("New vocab was successfully created."))
            .catch(error => console.log(error))
    }

    function editVocab(editedVocab: Vocab): void {
        axios.put(`api/vocab/${editedVocab._id}`, editedVocab)
            .then(response => console.log(response.data))
            .catch(error => console.error(error))
    }

    return (
        <form id={"form"} onSubmit={handleSubmit}>
            <label>Word</label>
            <input name={"word"}/>
            <label>Translation</label>
            <input name={"translation"}/>
            <label>Additional info, e.g.
                "colloquial"</label>
            <input name={"info"}/>
            <button>Submit</button>
        </form>
    )
}
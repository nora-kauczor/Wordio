import './DisplayPage.css'
import CardContainer
    from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";


type Props = {
    vocab: Vocab
}

export default function DisplayPage(props: Readonly<Props>) {

    return (
        <div id={"display-page"}>
            <p>Take your time to memorize the vocabulary</p>
            <CardContainer vocab={props.vocab}/>
            <button>activate</button>
        </div>
    )
}
import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";
import {Vocab} from "../../types/Vocab.ts";

type Props = {
    setShowPopUp: React.Dispatch<React.SetStateAction<boolean>>;
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>;
    vocabs:Vocab[]
}

export default function NewVocabsPopUp(props: Readonly<Props>) {

    function handleClick() {
        props.setShowPopUp(false)
        props.setUseForm(true)
    }

    function getRandomVocab():Vocab {
        // if (!props.vocabs) {return null}
        const numberOfVocabs: number = props.vocabs.length
        const randomIndex = Math.floor(Math.random() * numberOfVocabs)-1
        return props.vocabs[randomIndex];
    }

    function goToDisplayPageWithRandomVocab(){
        if (!getRandomVocab() || !getRandomVocab()._id) {return}
        const _id = getRandomVocab()._id
        navigate(`/display/${_id}`)
    }

    const navigate = useNavigate()
    return (<div id={"new-vocabs-popup"}>
        <button className={"new-vocabs-button"}
                onClick={handleClick}
                onKeyDown={handleClick}>Enter new
            vocab
        </button>
        <button className={"new-vocabs-button"}
                onClick={() => navigate("/backlog")}
                onKeyDown={() => navigate("/backlog")}>Pick
            from backlog
        </button>
        <button className={"new-vocabs-button"}
                onClick={goToDisplayPageWithRandomVocab}
                onKeyDown={goToDisplayPageWithRandomVocab}>get random
            vocab
        </button>
    </div>)
}
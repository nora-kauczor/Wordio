import './NewVocabsPopUp.css'
import {useNavigate} from "react-router-dom";

type Props = {
    setShowPopUp: React.Dispatch<React.SetStateAction<boolean>>;
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function NewVocabsPopUp(props: Readonly<Props>) {

    function handleClick() {
        props.setShowPopUp(false)
        props.setUseForm(true)
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
        <button className={"new-vocabs-button"}>get random
            vocab
        </button>
    </div>)
}
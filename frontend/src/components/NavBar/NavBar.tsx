import {useNavigate} from "react-router-dom";
import './NavBar.css'

type Props = {
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function NavBar(props: Readonly<Props>) {
    const navigate = useNavigate();
    return (
        <ul id={"navbar"}>
            <li id={"navbar-backlog"} onClick={() =>
                navigate('/backlog')}
                onKeyDown={() =>
                    navigate('/backlog')
                }>Backlog
            </li>
            <li id={"navbar-create"}
                onClick={() => props.setUseForm(true)}
                onKeyDown={() => props.setUseForm(true)}
            >Create
            </li>
            <li id={"navbar-calendar"}
                onClick={() =>
                    navigate('/calendar')
                }
                onKeyDown={() =>
                    navigate('/calendar')
                }
            >Calendar
            </li>
        </ul>
    )
}
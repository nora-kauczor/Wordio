import {useNavigate} from "react-router-dom";
import './NavBar.css'
import React from "react";

type Props = {
    useForm: boolean
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>;
}


export default function NavBar(props: Readonly<Props>) {
    const navigate = useNavigate();
    const path = window.location.pathname

    return (<ul id={"navbar"}>
        <li onClick={() => navigate('/backlog')}
            className={`navbar-button ${path === '/backlog' && !props.useForm ?
                'highlighted' : ''}`}
            aria-label={"Go to Backlog"}
        ><p className={"navbar-button-text"}>Backlog</p>
        </li>
        <li
            onClick={() => props.setUseForm(true)}
            className={`navbar-button ${props.useForm ? 'highlighted' : ''}`}
            aria-label={"Create a new item"}
        ><p className={"navbar-button-text"}>Create</p>
        </li>
        <li
            onClick={() => {
                navigate('/calendar')
            }}
            className={`navbar-button ${path === '/calendar' && !props.useForm ?
                'highlighted' : ''}`}
            aria-label={"Go to Calendar"}

        ><p className={"navbar-button-text"}>Calendar</p>
        </li>
    </ul>)
}
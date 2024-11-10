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
    const handleKeyDown = (event: React.KeyboardEvent<HTMLLIElement>,
                           path: string) => {
        if (event.key === 'Enter' || event.key === ' ') {
            navigate(path);
        }
    };
    return (<ul id={"navbar"}>
            <li onClick={() => navigate('/backlog')}
                onKeyDown={(event) => handleKeyDown(event, '/backlog')}
                className={`navbar-button ${path === '/backlog' && !props.useForm ?
                    'highlighted' : ''}`}
                aria-label={"Go to Backlog"}
            ><p className={"navbar-button-text"}>Backlog</p>
            </li>
            <li
                onClick={() => props.setUseForm(true)}
                onKeyDown={(event) => handleKeyDown(event, '')}
                className={`navbar-button ${props.useForm ? 'highlighted' :
                    ''}`}
                aria-label={"Create a new item"}
            ><p className={"navbar-button-text"}>Create</p>
            </li>
            <li
                onClick={() => navigate('/calendar')}
                onKeyDown={(event) => handleKeyDown(event, '/calendar')}
                className={`navbar-button ${path === '/calendar' && !props.useForm  ?
                    'highlighted' : ''}`}
                aria-label={"Go to Calendar"}

            ><p className={"navbar-button-text"}>Calendar</p>
            </li>
        </ul>)
}
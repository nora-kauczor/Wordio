import {useNavigate} from "react-router-dom";
import './NavBar.css'
import React from "react";

type Props = {
    setUseForm: React.Dispatch<React.SetStateAction<boolean>>;
}


export default function NavBar(props: Readonly<Props>) {
    const navigate = useNavigate();
    const handleKeyDown = (event: React.KeyboardEvent<HTMLLIElement>,
                           path: string) => {
        if (event.key === 'Enter' || event.key === ' ') {
            navigate(path);
        }
    };
    return (
        <ul id={"navbar"}>
            <li  onClick={() =>
                navigate('/backlog')}
                 onKeyDown={(event) => handleKeyDown(event, '/backlog')}
                 aria-label={"Go to Backlog"}
            >Backlog
            </li>
            <li
                onClick={() => props.setUseForm(true)}
                onKeyDown={(event) => handleKeyDown(event, '')}
                aria-label={"Create a new item"}
            >Create
            </li>
            <li
                onClick={() =>
                    navigate('/calendar')
                }
                onKeyDown={(event) => handleKeyDown(event, '/calendar')}
                aria-label={"Go to Calendar"}
            >Calendar
            </li>
        </ul>
    )
}
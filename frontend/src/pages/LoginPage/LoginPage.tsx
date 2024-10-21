import './LoginPage.css'
import {useEffect} from "react";
import axios from "axios";

type Props ={
    setUserName: React.Dispatch<React.SetStateAction<string>>;
}

export default function LoginPage(props:Readonly<Props>) {

    function login() {
        const host = window.location.host === "localhost:5173" ?
            "http://localhost:8080"
            : window.location.origin
        window.open(host + "/oauth2/authorization/github", "_self")
    }

    // useEffect(() => {
    //     axios.get("/api/vocab/auth")
    //         .then(response => props.setUserName(response.data.name))
    //         .catch(error => console.error(error))
    // }, [login, props.setUserName]);

    // axios.get("/api/vocab/auth")
    //     .then(response => console.log(response.data))
    //     .catch(error => console.error(error))

    return (
        <div id={"login-page"}>
          <h1>This is the LoginPage</h1>
            <button onClick={login} onKeyDown={login}>Login</button>
        </div>
    )
}
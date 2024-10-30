import './LoginPage.css'




export default function LoginPage() {

    function login() {
        console.log("login button was clicked.")
        const host = window.location.host === "localhost:5173" ?
            "http://localhost:8080"
            : window.location.origin
        window.open(host + "/oauth2/authorization/github", "_self")
    }

    return (
        <div id={"login-page"}>
            <button id={"login-button"} onClick={login} onKeyDown={login}>Login</button>
        </div>
    )
}
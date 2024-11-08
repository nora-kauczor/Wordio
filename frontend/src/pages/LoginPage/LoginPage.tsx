import './LoginPage.css'


export default function LoginPage() {

    function login() {
        const host = window.location.host === "localhost:5173" ?
            "http://localhost:8080" : window.location.origin
        window.open(host + "/oauth2/authorization/github", "_self")
    }

    return (<div id={"login-page"} className={"page"} role={"main"}>
            <button id={"login-button"}
                    onClick={login}
                    aria-label={"Login with GitHub"}>Login
            </button>
        </div>)
}
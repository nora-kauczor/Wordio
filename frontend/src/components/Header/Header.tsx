import './Header.css'

type Props = {
    userName: string
    logout: ()=> void
}

export default function Header(props:Readonly<Props>) {
    return (<div id={"header"}>

        <p id={"app-name"}>Wordio</p>
        {props.userName && <button id={"logout-button"}onClick={props.logout}>logout</button>}
    </div>)
}
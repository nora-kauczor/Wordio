

type Props = {
    day:string
}

export default function CalendarDay(props:Readonly<Props>) {
    return (<div>
<p>{props.day}</p>

    </div>)
}
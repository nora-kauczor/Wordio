import './CardContainer.css'

export default function CardContainer(){
    return(
        <div id={"card-container"}>
            <article id={"word-card"} className={"card"}></article>
            <article id={"translation-card"} className={"card"}></article>
        </div>
    )
}
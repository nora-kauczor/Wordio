import './Form.css'

type Props = {
    createVocab: ()
}

export default function Form(props: Readonly<Props>){
    function handleSubmit(event: React.FormEvent<HTMLFormElement>){
        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData);
        props.createVocab(data)
    }

    return(
        <form id={"form"} onSubmit={handleSubmit}>


        </form>
    )
}
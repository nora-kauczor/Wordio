import {Navigate, Outlet} from "react-router-dom";

type Props ={
    userName:string
}

export default function ProtectedRoutes(props: Props) {
    if (props.userName === undefined) {
        return <div>Loading...</div>;
    }

    return props.userName ? <Outlet /> : <Navigate to="/login" />;
}
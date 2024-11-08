import {Navigate, Outlet} from "react-router-dom";

type Props ={
    userId:string
}

export default function ProtectedRoutes(props: Props) {
    if (props.userId === undefined) {
        return <div>Loading...</div>;
    }

    return props.userId ? <Outlet /> : <Navigate to="/login" />;
}
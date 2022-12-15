import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ForbiddenPage from "./ForbiddenPage";
import ManagerProfilePage from "./ManagerProfilePage";
import PlayerProfilePage from "./PlayerProfilePage";

function ProfilePage() {
    const [role, setRole] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/user/whoami", { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                console.log(response);
                setRole(response.data.roles[0]);
            })
            .catch(error => {
                console.log(error);
                navigate("/forbidden");
            });
    }, []);

    if (role === "ROLE_PLAYER") {
        return (
            <PlayerProfilePage />
        )
    } else if (role === "ROLE_MANAGER") {
        return (
            <ManagerProfilePage managerEmail={localStorage.getItem("subject")} />
        )
    } else {
        return (
            <ForbiddenPage />
        )
    }

}

export default ProfilePage;
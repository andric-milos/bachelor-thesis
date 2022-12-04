import axios from "axios";
import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import NewGroupModal from "../components/modals/NewGroupModal";
import UserInfo from "../components/UserInfo";

function PlayerProfilePage() {
    const [playerData, setPlayerData] = useState({});
    const [groups, setGroups] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/user/whoami", {headers: {'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")}})
            .then(response => {
                console.log(response);

                if (!response.data.roles.find(element => element === "ROLE_PLAYER")) {
                    navigate("/forbidden");
                }
            })
            .catch(error => {
                navigate("/forbidden");
            });

        axios.get("http://localhost:8080/player/email/" + localStorage.getItem("subject"))
            .then(response => {
                console.log(response);

                setPlayerData({
                    id: response.data.id,
                    email: response.data.email,
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    telephone: response.data.telephone,
                    numberOfGoals: response.data.numberOfGoals,
                    numberOfGames: response.data.numberOfGames,
                    numberOfGamesWon: response.data.numberOfGamesWon,
                    winningPercentage: response.data.winningPercentage,
                    goalsPerAppointment: response.data.goalsPerAppointment,
                    canceledJustBefore: response.data.canceledJustBefore
                });
            })
            .catch(error => {
                console.log(error);
            });

            axios.get("http://localhost:8080/group", {headers: {'Authorization' : 'Bearer ' + localStorage.getItem("accessToken")}})
                .then(response => {
                    console.log(response);

                    setGroups(response.data);
                })
                .catch(error => {
                    console.log(error);
                });
    }, []);

    return (
        <div className="d-flex p-2 mt-5 m-2 justify-content-between">
            <UserInfo
                firstName={playerData.firstName}
                lastName={playerData.lastName}
                email={playerData.email}
                telephone={playerData.telephone}
            />

            <div className="d-flex flex-column col-sm-7">
                <h1>Personal statistics</h1>

                <label><b>Overall number of games played: {playerData.numberOfGames}</b></label>
                <label><b>Number of games won: {playerData.numberOfGamesWon}</b></label>
                <label><b>Overall number of goals scored: {playerData.numberOfGoals}</b></label>
                <label><b>Goals per appointment: {playerData.goalsPerAppointment}</b></label>
                <label><b>Winning percentage: {playerData.winningPercentage} %</b></label>

                <br></br><br></br><br></br>
                <div className="d-flex flex-row justify-content-between">
                    <h1>My groups</h1>
                    <NewGroupModal />
                </div>

                {groups.map((group, index) => {
                    return (
                        <div className="card my-2" key={`div-card-${group.id}`}>
                            <div 
                                className="card-body d-flex flex-row justify-content-between" 
                                key={`div-card-body-${group.id}`}
                            >
                                <label className="my-2"><b>{group.name}</b></label>
                                <Button variant="secondary">View group</Button>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    );
}

export default PlayerProfilePage;
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import NewGameModal from "../components/modals/NewGameModal";

function AppointmentPage() {
    const [appointmentState, setAppointmentState] = useState({
        capacity: 10,
        date: 1671130800000,
        games: [],
        groupId: 2,
        groupName: "Group name",
        id: 1,
        location: {
            id: 1,
            address: "Hajduk Veljkova 4",
            longitude: 0,
            latitude: 0
        },
        occupancy: 7,
        playersEmails: ["a@gmail.com", "b@gmail.com", "c@gmail.com", "d@gmail.com", "e@gmail.com", "f@gmail.com", "g@gmail.com"],
        price: 3800,
        privacy: "private"
    });

    const navigate = useNavigate();
    let { appointmentId } = useParams();

    useEffect(() => {
        axios.get("http://localhost:8080/appointment/" + appointmentId, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                console.log(response.data);
                setAppointmentState(response.data);
            })
            .catch(error => {
                try {
                    if (error.response.status == 403) {
                        navigate("/forbidden");
                    } else if (error.response.status == 404) {
                        navigate("/error");
                    }
                } catch (exception) {
                    console.log(exception);
                    navigate("/forbidden");
                }
            });
    }, []);

    const formatDate = (milliseconds) => {
        const options = {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit"
        };
        let date = new Date(milliseconds);
        return date.toLocaleDateString(undefined, options);
    }

    return (
        <div className="d-flex flex-column p-2 mt-5 m-2 justify-content-center">
            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <img src="https://joomly.net/frontend/web/images/googlemap/map.png" alt="map icon" />
                </div>
                <div className="d-flex flex-column col-sm-6 mx-3">
                    <h1><b>{appointmentState.groupName}</b></h1>
                    <div className="px-2 d-flex flex-column">
                        <label><b>{formatDate(appointmentState.date)}</b></label>
                        <label><b>Address: {appointmentState.location.address}</b></label>
                        <label><b>Occupancy: {appointmentState.occupancy}/{appointmentState.capacity}</b></label>
                        <label><b>Privacy: {appointmentState.privacy}</b></label>
                        <label><b>Price: {appointmentState.price} RSD</b></label>
                    </div>
                </div>
            </div>

            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <h3><b>Attending players [{appointmentState.occupancy}/{appointmentState.capacity}]</b></h3>

                    {appointmentState.playersEmails.length != 0 ? appointmentState.playersEmails.map((email, index) => {
                        return (
                            <div className="card my-1" key={`div-card-player-${index}`}>
                                <div className="card-body" key={`div-cardBody-player-${index}`}>
                                    <label key={`label-player-${index}`}><b>{email}</b></label>
                                </div>
                            </div>
                        )
                    }) : <label>There are no attendees.</label>}
                </div>

                <div className="d-flex flex-column col-sm-6 mx-3">
                    <div className="d-flex flex-row justify-content-between">
                        <h3><b>Games</b></h3>
                        <NewGameModal players={appointmentState.playersEmails} />
                    </div>

                    {appointmentState.games.length != 0 ? appointmentState.games.map(game => {
                        return (
                            <div className="card my-1" key={`div-card-game-${game.id}`}>
                                <div className="card-body d-flex flex-column" key={`div-cardBody-player-${game.id}`}>
                                    <label className="h1" align="center" key={`label-game-${game.id}`}>
                                        <b>Team Red {game.goalsTeamRed} - {game.goalsTeamBlue} Team Blue</b>
                                    </label>
                                </div>
                            </div>
                        )
                    }) : <label>There are no games added.</label>}
                </div>
            </div>
        </div>
    )
}

export default AppointmentPage;
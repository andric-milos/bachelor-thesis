import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import NewAppointmentModal from "../components/modals/NewAppointmentModal";

function GroupPage() {
    const navigate = useNavigate();
    let { groupId } = useParams();

    const [groupWithPlayers, setGroupWithPlayers] = useState({
        name: "Group name",
        playersEmails: []
    });
    const [appointments, setAppointments] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/group/" + groupId, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                console.log(response);
                setGroupWithPlayers(response.data);

                axios.get("http://localhost:8080/appointment/group/" + groupId)
                    .then(response => {
                        console.log(response);
                        setAppointments(response.data);
                    })
                    .catch(error => {
                        try {
                            if (error.response.status == 403) {
                                navigate("/forbidden");
                            } else if (error.response.status == 404) {
                                navigate("/error");
                            }
                        } catch (error) {
                            console.log(error);
                            navigate("/forbidden");
                        }
                    });
            })
            .catch(error => {
                try {
                    if (error.response.status == 403) {
                        navigate("/forbidden");
                    } else if (error.response.status == 404) {
                        navigate("/error");
                    }
                } catch (error) {
                    console.log(error);
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
            <h1 align="center"><b>{groupWithPlayers.name}</b></h1>

            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <h3><b>Players</b></h3>

                    {groupWithPlayers.playersEmails.length != 0 ? groupWithPlayers.playersEmails.map((email, index) => {
                        return (
                            <div className="card my-1" key={`div-card-email-${index}`}>
                                <div className="card-body" key={`div-cardBody-email-${index}`}>
                                    <label key={`label-email-${index}`} ><b>{email}</b></label>
                                </div>
                            </div>
                        )
                    }) : <label>There are no players within this group yet.</label>}
                </div>
                <div className="d-flex flex-column col-sm-6">
                    <div className="d-flex flex-row justify-content-between">
                        <h3><b>Appointments</b></h3>
                        <NewAppointmentModal groupId={groupId} />
                    </div>

                    {appointments.length != 0 ? appointments.map((appointment, index) => {
                        return (
                            <div className="card my-1" key={`div-card-appmnt-${index}`}>
                                <div className="card-body d-flex flex-column" key={`div-cardBody-appmnt-${index}`}>
                                    <label key={`label1-appmnt-${index}`}><b>{formatDate(appointment.date)}</b></label>
                                    <label key={`label2-appmnt-${index}`}><b>Address: {appointment.location.address}</b></label>
                                    <label key={`label3-appmnt-${index}`}><b>Capacity: {appointment.occupancy}/{appointment.capacity}</b></label>
                                </div>
                            </div>
                        )
                    }) : <label>There are no appointments within this group yet.</label>}
                </div>
            </div>
        </div>
    );
}

export default GroupPage;
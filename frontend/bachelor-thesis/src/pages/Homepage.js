import axios from "axios";
import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";

function Homepage() {
    const [appointments, setAppointments] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/appointment/")
            .then(response => {
                console.log(response.data);
                setAppointments(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, [])

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

    const navigateToAppointmentPage = (appointmentId) => {
        navigate("/appointment/" + appointmentId);
    }

    return (
        <div className="d-flex flex-row justify-content-center">
            <div className="d-flex flex-column p-2 col-sm-5">
                {appointments.map((appointment) => {
                    return (
                        <div className="d-flex card my-1" key={`div-card-appmnt-${appointment.id}`}>
                            <div className="card-body d-flex flex-column" key={`div-cardBody-appmnt-${appointment.id}`}>
                                <label key={`label1-appmnt-${appointment.id}`} className="h3"><b>{appointment.groupName}</b></label>
                                <label key={`label2-appmnt-${appointment.id}`}><b>{formatDate(appointment.date)}</b></label>
                                <label key={`label3-appmnt-${appointment.id}`}><b>Address: {appointment.location.address}</b></label>
                                <label key={`label4-appmnt-${appointment.id}`}><b>Capacity: {appointment.occupancy}/{appointment.capacity}</b></label>
                                <label key={`label5-appmnt-${appointment.id}`}><b>Price: {appointment.price} RSD</b></label>
                                <Button variant="primary" onClick={() => navigateToAppointmentPage(appointment.id)}>View appointment</Button>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default Homepage;
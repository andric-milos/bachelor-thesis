import axios from "axios";
import { useEffect, useState } from "react";

function SportsFacilitiesPage() {
    const [sportsFacilities, setSportsFacilites] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/sportsFacility")
            .then(response => {
                console.log(response.data);
                setSportsFacilites(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <div className="d-flex flex-row justify-content-center">
            <div className="d-flex flex-column p-2 col-sm-5">
                {sportsFacilities.map((facility) => {
                    return (
                        <div className="d-flex card my-1" key={`div-card-facility-${facility.id}`}>
                            <div className="card-body d-flex flex-column" key={`div-cardBody-facility-${facility.id}`}>
                                <label key={`label1-facility-${facility.id}`} className="h3"><b>{facility.name}</b></label>
                                <label key={`label2-facility-${facility.id}`}><b>Address: {facility.location.address}</b></label>
                                <label key={`label3-facility-${facility.id}`}><b>Price per hour: {facility.pricePerHour} RSD</b></label>
                                <label key={`label4-facility-${facility.id}`}><b>Manager: {facility.managerFirstName} {facility.managerLastName}</b></label>
                                <label key={`label5-facility-${facility.id}`}><b>Manager's email: {facility.managerEmail}</b></label>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default SportsFacilitiesPage;
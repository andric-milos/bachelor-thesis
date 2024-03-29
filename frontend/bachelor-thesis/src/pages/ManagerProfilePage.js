import axios from "axios";
import { useEffect, useState } from "react";
import AddSportsFacilityModal from "../components/modals/AddSportsFacilityModal";
import UserInfo from "../components/UserInfo";

function ManagerProfilePage({ managerEmail }) {
    const [manager, setManager] = useState({});

    useEffect(() => {
        axios.get("http://localhost:8080/manager/email/" + managerEmail)
            .then(response => {
                setManager(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <div className="d-flex p-2 mt-5 m-2 justify-content-between">
            <UserInfo
                firstName={manager.firstName}
                lastName={manager.lastName}
                email={manager.email}
                telephone={manager.telephone}
            />

            <div className="d-flex flex-column col-sm-7">
                <h1>Sports facility</h1>

                {manager.sportsFacility ?
                    <>
                        <label><b>Name: {manager.sportsFacility.name}</b></label>
                        <label><b>Price per hour: {manager.sportsFacility.pricePerHour} RSD</b></label>
                        <label><b>Address: {manager.sportsFacility.location.address}</b></label>
                        <img src="gdmMap.PNG" alt="map icon" />
                    </> :
                    <>
                        <label>You haven't added a sports facility.</label>
                        <AddSportsFacilityModal />
                    </>
                }
            </div>
        </div>
    )
}

export default ManagerProfilePage;
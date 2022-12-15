import axios from "axios";
import { useEffect, useState } from "react";
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
                    <div>
                        <label><b>Name: GDM sportski centar</b></label>
                        <label><b>Open hours: 12-24</b></label>
                        <label><b>Price per hour: 3800 RSD</b></label>
                        <label><b>Address: Hajduk Veljkova 11</b></label>
                        <img src="https://joomly.net/frontend/web/images/googlemap/map.png" alt="map icon" />
                    </div>
                : <label>You haven't added a sports facility.</label>}
                
            </div>
        </div>
    )
}

export default ManagerProfilePage;
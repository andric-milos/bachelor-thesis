function AppointmentPage() {
    return (
        <div className="d-flex flex-column p-2 mt-5 m-2 justify-content-center">
            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <img src="https://joomly.net/frontend/web/images/googlemap/map.png" alt="map icon" />
                </div>
                <div className="d-flex flex-column col-sm-6 mx-3">
                    <h1><b>Group name</b></h1>
                    <div className="px-2 d-flex flex-column">
                        <label><b>Date: 03.11.2022. 20:00</b></label>
                        <label><b>Address: Hajduk Veljkova 4</b></label>
                        <label><b>Occupancy: 8/12</b></label>
                        <label><b>Privacy: public</b></label>
                        <label><b>Price: 3800 RSD</b></label>
                    </div>
                </div>
            </div>

            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <h3><b>Attending players [8/12]</b></h3>

                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>asfjasf@gmail.com</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>asfjasf@gmail.com</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>asfjasf@gmail.com</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>asfjasf@gmail.com</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>asfjasf@gmail.com</b></label>
                        </div>
                    </div>
                </div>

                <div className="d-flex flex-column col-sm-6 mx-3">
                    <div className="d-flex flex-row justify-content-between">
                        <h3><b>Games</b></h3>
                        <button className="btn btn-primary">Add new</button>
                    </div>

                    <div className="card my-1">
                        <div className="card-body d-flex flex-column">
                            <label className="h1" align="center"><b>Team Red 3 - 2 Team Blue</b></label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AppointmentPage;
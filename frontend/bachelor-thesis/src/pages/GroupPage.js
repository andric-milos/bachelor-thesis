import NewAppointmentModal from "../components/modals/NewAppointmentModal";

function GroupPage() {
    return (
        <div className="d-flex flex-column p-2 mt-5 m-2 justify-content-center">
            <h1 align="center"><b>Naziv grupe</b></h1>

            <div className="d-flex flex-row mt-4">
                <div className="d-flex flex-column col-sm-6">
                    <h3><b>Players</b></h3>

                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>John Doe</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>John Doe</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body">
                            <label><b>John Doe</b></label>
                        </div>
                    </div>
                </div>
                <div className="d-flex flex-column col-sm-6">
                    <div className="d-flex flex-row justify-content-between">
                        <h3><b>Appointments</b></h3>
                        <NewAppointmentModal />
                    </div>

                    <div className="card my-1">
                        <div className="card-body d-flex flex-column">
                            <label><b>20.11.2022. 18:00</b></label>
                            <label><b>Hajduk Veljkova 4</b></label>
                            <label><b>Capacity: 8/10</b></label>
                        </div>
                    </div>
                    <div className="card my-1">
                        <div className="card-body d-flex flex-column">
                            <label><b>27.11.2022. 18:00</b></label>
                            <label><b>Hajduk Veljkova 4</b></label>
                            <label><b>Capacity: 8/10</b></label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default GroupPage;
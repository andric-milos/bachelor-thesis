import NewGroupModal from "../components/modals/NewGroupModal";
import UserInfo from "../components/UserInfo";

function PlayerProfilePage() {
    return (
        <div className="d-flex p-2 mt-5 m-2 justify-content-between">
            <UserInfo />

            <div className="d-flex flex-column col-sm-7">
                <h1>Personal statistics</h1>

                <label><b>Overall number of games played: 100</b></label>
                <label><b>Number of games won: 55</b></label>
                <label><b>Overall number of goals scored: 70</b></label>
                <label><b>Goals per appointment: 1.54</b></label>
                <label><b>Winning percentage: 55%</b></label>

                <br></br><br></br><br></br>
                <div className="d-flex flex-row justify-content-between">
                    <h1>My groups</h1>
                    <NewGroupModal />
                </div>

                <div className="card my-2">
                    <div className="card-body">
                        Group 1
                    </div>
                </div>
                <div className="card my-2">
                    <div className="card-body">
                        Fudbal četvrtkom u 20h
                    </div>
                </div>
                <div className="card my-2">
                    <div className="card-body">
                        Fudbal utorkom u 21h
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PlayerProfilePage;
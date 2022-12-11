import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { useState } from "react";
import AddTeamModal from "./AddTeamModal";
import AddGoalModal from "./AddGoalModal";

function NewGameModal({ players }) {
    const [show, setShow] = useState(false);
    const [teamRed, setTeamRed] = useState([]);
    const [teamBlue, setTeamBlue] = useState([]);
    const [goals, setGoals] = useState([]);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const addTeamRed = (players) => setTeamRed(players);
    const addTeamBlue = (players) => setTeamBlue(players);

    const addGoal = (goal) => {
        let tmp = goals.slice();
        tmp.push(goal);
        setGoals(tmp);
    }

    const outputToConsole = () => {
        console.log("Team Red:" + teamRed);
        console.log("Team Blue:" + teamBlue);

        console.log("Goals: ");
        for (let i = 0; i < goals.length; i++) {
            console.log(goals[i].player + " " + goals[i].teamColor);
        }
        
    }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Add new</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add new game</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-row justify-content-between">
                        <label><b>Team Red</b></label>
                        <AddTeamModal caption="Add Team Red" players={players} addTeam={addTeamRed} />
                    </div>
                    <div className="card my-1">
                        {teamRed.length != 0 ? teamRed.map((email, index) => {
                            return (
                                <div className="card-body" key={`div-cardBody-email-${index}`}>
                                    <label key={`label-email-${index}`} ><b>{index + 1}. {email}</b></label>
                                </div>
                            )
                        }) : <label>There are no players in Team Red!</label>}
                    </div>

                    <div className="d-flex flex-row justify-content-between">
                        <label><b>Team Blue</b></label>
                        <AddTeamModal caption="Add Team Blue" players={players} addTeam={addTeamBlue} />
                    </div>
                    <div className="card my-1">
                        {teamBlue.length != 0 ? teamBlue.map((email, index) => {
                            return (
                                <div className="card-body" key={`div-cardBody-email-${index}`}>
                                    <label key={`label-email-${index}`} ><b>{index + 1}. {email}</b></label>
                                </div>
                            )
                        }) : <label>There are no players in Team Blue!</label>}
                    </div>

                    <div className="d-flex flex-row justify-content-between">
                        <label><b>Goals</b></label>
                        <AddGoalModal teamRedPlayers={teamRed} teamBluePlayers={teamBlue} addGoal={addGoal} />
                    </div>
                    
                    <div className="card my-1">
                        {goals.length != 0 ? goals.map((goal, index) => {
                            return (
                                <div className="card-body" key={`div-cardBody-goal-${index}`}>
                                    <label key={`label-goal-${index}`} ><b>{goal.player}[team {goal.teamColor}]</b></label>
                                </div>
                            )
                        }) : <label>There are no goals!</label>}
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary" onClick={outputToConsole}>Submit</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default NewGameModal;
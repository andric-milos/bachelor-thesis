import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { useState } from "react";
import AddTeamModal from "./AddTeamModal";
import AddGoalModal from "./AddGoalModal";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

function NewGameModal({ players }) {
    const [show, setShow] = useState(false);
    const [teamRed, setTeamRed] = useState([]);
    const [teamBlue, setTeamBlue] = useState([]);
    const [goals, setGoals] = useState([]);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const addTeamRed = (players) => setTeamRed(players);
    const addTeamBlue = (players) => setTeamBlue(players);

    let { appointmentId } = useParams();
    const navigate = useNavigate();

    const addGoal = (goal) => {
        let tmp = goals.slice();
        tmp.push(goal);
        setGoals(tmp);
    }

    const submitHandler = () => {
        const dto = {
            "appointmentId": appointmentId,
            "teamRed": teamRed,
            "teamBlue": teamBlue,
            "goals": goals
        };

        axios.post("http://localhost:8080/appointment/game", dto, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                if (response.status == 200) {
                    alert("You succesfully added a new game");
                    navigate(0);
                }
            })
            .catch(error => {
                try {
                    if (error.response.status == 403) {
                        navigate("/forbidden");
                    } else if (error.response.status == 404) {
                        navigate("/error");
                    } else if (error.response.status == 400) {
                        alert(error.response.data);
                    }
                } catch (exception) {
                    console.log(exception);
                    navigate("/forbidden");
                }
            });
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
                    <Button variant="primary" onClick={submitHandler}>Submit</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default NewGameModal;
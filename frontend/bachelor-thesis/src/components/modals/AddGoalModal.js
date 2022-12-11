import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { useRef, useState } from "react";

function AddGoalModal({ teamRedPlayers, teamBluePlayers, addGoal }) {
    const [show, setShow] = useState(false);
    const [teamColorState, setTeamColorState] = useState("red");

    const teamSelectRef = useRef();
    const playerSelectRef = useRef();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const outputToConsole = () => {
        console.log("teamRedPlayers: " + teamRedPlayers);
        console.log("team selected: " + teamSelectRef.current.value);
    }

    const teamColorOnChangeHandler = () => {
        setTeamColorState(teamSelectRef.current.value);
    }

    const submitHandler = () => {
        const goal = {
            teamColor: teamSelectRef.current.value,
            player: playerSelectRef.current.value
        };

        addGoal(goal);
        handleClose();
    }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Add goal</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add goal</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column">
                        <label className="p-2"><b>Team</b></label>
                        <select name="team" className="p-2" ref={teamSelectRef} onChange={teamColorOnChangeHandler}>
                            <option value="red">Red</option>
                            <option value="blue">Blue</option>
                        </select>

                        <label className="p-2 mt-2"><b>Player</b></label>
                        <select name="player" className="p-2" ref={playerSelectRef}>
                            {teamColorState === "red" ? teamRedPlayers.map((playerEmail, index) => {
                                return (
                                    <option value={playerEmail} key={`option-red-${index}`}>{playerEmail}</option>
                                )
                            }) : teamBluePlayers.map((playerEmail, index) => {
                                return (
                                    <option value={playerEmail} key={`option-blue-${index}`}>{playerEmail}</option>
                                )
                            })}
                        </select>
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

export default AddGoalModal;
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { useEffect, useState } from "react";
import { ToggleButton } from "react-bootstrap";

function AddTeamModal({ caption, addTeam, players }) {
    const [show, setShow] = useState(false);
    const [playersState, setPlayersState] = useState([]);
    const [checkedState, setCheckedState] = useState([]);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        initializeCheckedState(players.length);
    }, []);

    const selectPlayerHandler = (event, playerEmail, checkedIndex) => {
        if (!playersState.find(element => element === playerEmail)) {
            event.target.textContent = "Selected";
            changeSelectedPlayersStateImmutably("ADD", playerEmail);
            changeCheckedStateImmutably(checkedIndex, true);
        } else {
            event.target.textContent = "Select";
            changeSelectedPlayersStateImmutably("REMOVE", playerEmail);
            changeCheckedStateImmutably(checkedIndex, false);
        }
    }

    const changeCheckedStateImmutably = (index, value) => {
        const tmpChecked = checkedState.slice();
        tmpChecked[index] = value;
        setCheckedState(tmpChecked);
    }

    const changeSelectedPlayersStateImmutably = (addOrRemove, email) => {
        const tmpSelectedPlayers = playersState.slice();
        if (addOrRemove === "ADD") {
            tmpSelectedPlayers.push(email);
        }
        else if (addOrRemove === "REMOVE") {
            tmpSelectedPlayers.splice(tmpSelectedPlayers.indexOf(email), 1);
        }
        setPlayersState(tmpSelectedPlayers);
    }

    const initializeCheckedState = (length) => {
        let tmpChecked = [];
        for (let i = 0; i < length; i++) {
            tmpChecked.push(false);
        }
        setCheckedState(tmpChecked);
    }

    const submitHandler = () => {
        addTeam(playersState);
        handleClose();
    }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>{caption}</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{caption}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {players.map((playerEmail, index) => {
                        return (
                            <div className="card my-1" key={`div-card-email-${index}`}>
                                <div 
                                    className="card-body d-flex flex-row justify-content-between"
                                    key={`div-cardBody-email-${index}`}
                                >
                                    <label key={`label-email-${index}`} ><b>{playerEmail}</b></label>
                                    <ToggleButton 
                                        variant="outline-success"
                                        id={`toggle-button-${index}`}
                                        type="checkbox"
                                        onClick={event => selectPlayerHandler(event, playerEmail, index)}
                                        checked={checkedState[index]}
                                    >Select</ToggleButton>
                                </div>
                            </div>
                        )
                    })}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary" onClick={submitHandler}>Submit</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default AddTeamModal;
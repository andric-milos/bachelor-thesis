import { useEffect, useState } from "react";

import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import axios from "axios";
import ToggleButton from "react-bootstrap/ToggleButton";

function NewGroupModal(props) {
    const [show, setShow] = useState(false);
    const [players, setPlayers] = useState([]);
    const [checked, setChecked] = useState([]);
    const [selectedPlayers, setSelectedPlayers] = useState([]);

    const handleClose = () => {
        setShow(false);
        setSelectedPlayers([]);
        initializeCheckedArray(players.length);
    }

    const handleShow = () => setShow(true);    

    // Fetching players from the database, when the component is mounted.
    useEffect(() => {
        axios.get("http://localhost:8080/player/")
            .then(response => {
                setPlayers(response.data);
                initializeCheckedArray(response.data.length);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const selectPlayerHandler = (event, email, checkedIndex) => {
        if (!selectedPlayers.find(element => element === email)) {
            event.target.textContent = "Selected";
            changeSelectedPlayersStateImmutably("ADD", email);
            changeCheckedStateImmutably(checkedIndex, true);
        } else {
            event.target.textContent = "Select";
            changeSelectedPlayersStateImmutably("REMOVE", email);
            changeCheckedStateImmutably(checkedIndex, false);
        }
    }

    const createGroupHandler = () => {
        console.log(selectedPlayers);
    }

    const initializeCheckedArray = (length) => {
        let tmpChecked = [];
        for (let i = 0; i < length; i++) {
            tmpChecked.push(false);
        }
        setChecked(tmpChecked);
    }

    const changeCheckedStateImmutably = (index, value) => {
        const tmpChecked = checked.slice();
        tmpChecked[index] = value;
        setChecked(tmpChecked);
    }

    const changeSelectedPlayersStateImmutably = (addOrRemove, email) => {
        const tmpSelectedPlayers = selectedPlayers.slice();
        if (addOrRemove === "ADD") {
            tmpSelectedPlayers.push(email);
        }
        else if (addOrRemove === "REMOVE") {
            tmpSelectedPlayers.splice(tmpSelectedPlayers.indexOf(email), 1);
        }
        setSelectedPlayers(tmpSelectedPlayers);
    }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Create a group</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create a group</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {players.map((player, index) => {
                        return (
                            <div key={`div-${player.id}`} className="d-flex flex-row justify-content-between m-1">
                                <p key={`p-${player.id}`}><b>{player.email} ({player.firstName} {player.lastName})</b></p>
                                <ToggleButton 
                                    key={`tb-${player.id}`}
                                    id={`radio-${index}`}
                                    variant="outline-success" 
                                    type="checkbox" 
                                    onClick={event => selectPlayerHandler(event, player.email, index)}
                                    checked={checked[index]}
                                >Select</ToggleButton>
                            </div>
                        )
                    })}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary" onClick={createGroupHandler}>Create</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default NewGroupModal;
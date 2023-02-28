import { useEffect, useRef, useState } from "react";
import { useNavigate } from 'react-router-dom';

import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import axios from "axios";
import ToggleButton from "react-bootstrap/ToggleButton";

function NewGroupModal(props) {
    const [show, setShow] = useState(false);
    const [players, setPlayers] = useState([]);
    const [selectedPlayers, setSelectedPlayers] = useState([]);

    const nameInputRef = useRef();

    const navigate = useNavigate();

    const handleClose = () => {
        setShow(false);
        setSelectedPlayers([]);
    };

    const handleShow = () => setShow(true);

    // Fetching players from the database, when the component is mounted.
    useEffect(() => {
        axios.get("http://localhost:8080/player/")
            .then(response => {
                setPlayers(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const selectPlayerHandler = (event, email) => {
        if (!isPlayerSelected(email)) {
            event.target.textContent = "Selected";
            changeSelectedPlayersStateImmutably("ADD", email);
        } else {
            event.target.textContent = "Select";
            changeSelectedPlayersStateImmutably("REMOVE", email);
        }
    };

    const createGroupHandler = () => {
        let dto = {
            "name": nameInputRef.current.value,
            "playersEmails": selectedPlayers
        };

        // console.log(selectedPlayers);
        console.log(dto);

        axios.post("http://localhost:8080/group/", dto, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                alert("You successfully created a new group!");
                navigate(0); // Refreshing.
            })
            .catch(error => {
                console.log(error);
                alert(error.message);
            });
    };

    const changeSelectedPlayersStateImmutably = (addOrRemove, email) => {
        const tmpSelectedPlayers = selectedPlayers.slice();
        if (addOrRemove === "ADD") {
            tmpSelectedPlayers.push(email);
        }
        else if (addOrRemove === "REMOVE") {
            tmpSelectedPlayers.splice(tmpSelectedPlayers.indexOf(email), 1);
        }
        setSelectedPlayers(tmpSelectedPlayers);
    };

    const isPlayerSelected = (email) => {
        if (selectedPlayers.find(element => element === email)) {
            return true;
        } else {
            return false;
        }
    };

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Create a group</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create a group</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex d-row m-1">
                        <label className="p-2" style={{ width: "35%" }}><b>Group name:</b></label>
                        <input type="text" className="p-2 w-100" id="name" ref={nameInputRef}></input>
                    </div>

                    {players.map((player, index) => {
                        return (
                            <div key={`div-${player.id}`} className="d-flex flex-row justify-content-between m-1">
                                <p key={`p-${player.id}`} className="px-2"><b>{player.email} ({player.firstName} {player.lastName})</b></p>
                                <ToggleButton
                                    key={`tb-${player.id}`}
                                    id={`radio-${index}`}
                                    variant="outline-success"
                                    type="checkbox"
                                    onClick={event => selectPlayerHandler(event, player.email)}
                                    checked={isPlayerSelected(player.email)}
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
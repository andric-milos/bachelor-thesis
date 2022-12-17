import axios from "axios";
import { useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";

import classes from './GamePreviewModal.module.css';

function GamePreviewModal(props) {
    const [show, setShow] = useState(false);
    const [game, setGame] = useState({
        id: null,
        goals: []
    });

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        axios.get("http://localhost:8080/game/" + props.gameId)
            .then(response => {
                setGame(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const calculateResult = (index) => {
        let goalsRed = 0;
        let goalsBlue = 0;

        for (let i = 0; i < index + 1; i++) {
            if (game.goals[i].teamColor === "red")
                ++goalsRed;
            else
                ++goalsBlue;
        }

        return goalsRed.toString() + " - " + goalsBlue.toString();
    }

    return (
        <div>
            <div onClick={handleShow}>
                {props.children}
            </div>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header>
                    <Modal.Title>Game preview</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column">
                        {game.goals.map((goal, index) => {
                            return (
                                <div key={`main-div-${index}`}>
                                    {goal.teamColor === "red" ?
                                        <div key={`main-div-red-${index}`} className="d-flex flex-row">
                                            <div key={`div-red-${index}`}>
                                            <img key={`img-red-${index}`} src="https://www.svgrepo.com/show/46358/football-ball.svg" height="50" width="50" alt="goal" />
                                                <label key={`label1-red-${index}`} className={classes["font-size-25"]}><b>{calculateResult(index)}</b></label>
                                                <label key={`label2-red-${index}`} className={classes["font-size-25"]}>
                                                    <b><span className={classes["goal-red"]}>{goal.player.firstName} {goal.player.lastName}</span></b>
                                                </label>
                                            </div>
                                        </div> :
                                        <div key={`main-div-blue-${index}`} className="d-flex flex-row justify-content-end">
                                            <div key={`div-blue-${index}`}>
                                                <label key={`label1-blue-${index}`} className={classes["font-size-25"]}>
                                                    <b><span className={classes["goal-blue"]}>{goal.player.firstName} {goal.player.lastName}</span></b>
                                                </label>
                                                <label key={`label2-blue-${index}`} className={classes["font-size-25"]}><b>{calculateResult(index)}</b></label>
                                                <img key={`img-blue-${index}`} src="https://www.svgrepo.com/show/46358/football-ball.svg" height="50" width="50" alt="goal" />
                                            </div>
                                        </div>
                                    }
                                </div>
                            )
                        })}
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default GamePreviewModal;
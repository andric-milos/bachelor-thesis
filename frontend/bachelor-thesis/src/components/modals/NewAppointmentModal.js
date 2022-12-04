import { useState, useRef } from "react";

import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

function NewAppointmentModal(props) { // Pass group id as a prop.
    const [show, setShow] = useState(false);

    const dateInputRef = useRef();
    const timeInputRef = useRef();
    const privacySelectRef = useRef();
    const capacityInputRef = useRef();
    const addressInputRef = useRef();
    const priceInputRef = useRef();

    const handleClose = () => {
        setShow(false);
    }

    const handleShow = () => setShow(true);

    const createAppointmentHandler = () => {
        let dto = {
            "date" : dateInputRef.current.value,
            "time" : timeInputRef.current.value,
            "privacy" : privacySelectRef.current.value,
            "capacity" : capacityInputRef.current.value,
            "address" : addressInputRef.current.value,
            "price" : priceInputRef.current.value
        };

        console.log(dto);
    }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Add new</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add new appointment</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column m-1">
                        <div className="d-flex flex-row justify-content-between">
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Date:</b></label>
                                <input type="date" className="p-2" ref={dateInputRef}></input>
                            </div>
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Time:</b></label>
                                <input type="time" className="p-2" ref={timeInputRef}></input>
                            </div>
                        </div>

                        <div className="d-flex flex-row justify-content-between">
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Privacy:</b></label>
                                <select name="privacy" className="p-2" ref={privacySelectRef}>
                                    <option value="private">Private</option>
                                    <option value="public">Public</option>
                                </select>
                            </div>
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Capacity:</b></label>
                                <input 
                                    type="number" 
                                    className="p-2" 
                                    min="2" max="14" 
                                    ref={capacityInputRef}>
                                </input>
                            </div>
                        </div>

                        <div className="d-flex flex-column m-1">
                            <label ><b>Address:</b></label>
                            <input type="text" className="p-2" ref={addressInputRef}></input>
                        </div>

                        <div className="d-flex flex-column m-1">
                            <label ><b>Price:</b></label>
                            <input 
                                type="text" 
                                className="p-2" 
                                placeholder="Price in RSD"
                                ref={priceInputRef}>
                            </input>
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary" onClick={createAppointmentHandler}>Submit</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default NewAppointmentModal;
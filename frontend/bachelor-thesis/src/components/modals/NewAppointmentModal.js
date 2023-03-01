import { useState, useRef } from "react";

import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function NewAppointmentModal(props) { // Pass group id as a prop.
    const navigate = useNavigate();

    const [show, setShow] = useState(false);

    const dateInputRef = useRef();
    const timeInputRef = useRef();
    const privacySelectRef = useRef();
    const capacityInputRef = useRef();
    const addressInputRef = useRef();
    const priceInputRef = useRef();

    const dateErrorLabelRef = useRef();
    const timeErrorLabelRef = useRef();
    const privacyErrorLabelRef = useRef();
    const capacityErrorLabelRef = useRef();
    const addressErrorLabelRef = useRef();
    const priceErrorLabelRef = useRef();

    const handleClose = () => {
        setShow(false);
    }

    const handleShow = () => setShow(true);

    const createAppointmentHandler = () => {
        if (formValidation()) {
            const dateAsMillis = Date.parse(dateInputRef.current.value);
            const hours = timeInputRef.current.value.substring(0, 2);
            const minutes = timeInputRef.current.value.substring(3, 5);
            
            let dto = {
                "groupId" : props.groupId,
                "date" : dateAsMillis + hours * 60 * 60 * 1000 + minutes * 60 * 1000,
                "privacy" : privacySelectRef.current.value,
                "capacity" : parseInt(capacityInputRef.current.value),
                "address" : addressInputRef.current.value,
                "price" : parseFloat(priceInputRef.current.value)
            };
    
            // console.log(dto);

            axios.post("http://localhost:8080/appointment", dto, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
                .then(response => {
                    console.log(response);

                    if (response.status === 200) {
                        alert("You successfully added a new appointment!");
                        navigate(0);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }

    /* If the form has invalid inputs, this function returns false. */
    const formValidation = () => {
        let hasErrors = 0;

        if (isNaN(Date.parse(dateInputRef.current.value))) {
            ++hasErrors;
            dateErrorLabelRef.current.innerHTML = "Invalid date input!";
        } else {
            dateErrorLabelRef.current.innerHTML = "";
        }

        if (!timeInputRef.current.value.match(/[012]\d:[012345]\d/)) {
            ++hasErrors;
            timeErrorLabelRef.current.innerHTML = "Invalid time input!";
        } else {
            timeErrorLabelRef.current.innerHTML = "";
        }

        if (!privacySelectRef.current.value) {
            ++hasErrors;
            privacyErrorLabelRef.current.innerHTML = "Invalid privacy input!";
        } else {
            privacyErrorLabelRef.current.innerHTML = "";
        }

        if (isNaN(parseInt(capacityInputRef.current.value))) {
            ++hasErrors;
            capacityErrorLabelRef.current.innerHTML = "Invalid capacity input!";
        } else {
            capacityErrorLabelRef.current.innerHTML = "";
        }

        if (!addressInputRef.current.value) {
            ++hasErrors;
            addressErrorLabelRef.current.innerHTML = "Invalid address input!";
        } else {
            addressErrorLabelRef.current.innerHTML = "";
        }

        if (isNaN(parseFloat(priceInputRef.current.value))) {
            ++hasErrors;
            priceErrorLabelRef.current.innerHTML = "Invalid price input!";
        } else {
            priceErrorLabelRef.current.innerHTML = "";
        }

        if (hasErrors)
            return false;
        
        return true;
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
                                <label className="text-danger" ref={dateErrorLabelRef}></label>
                            </div>
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Time:</b></label>
                                <input type="time" className="p-2" ref={timeInputRef}></input>
                                <label className="text-danger" ref={timeErrorLabelRef}></label>
                            </div>
                        </div>

                        <div className="d-flex flex-row justify-content-between">
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Privacy:</b></label>
                                <select name="privacy" className="p-2" ref={privacySelectRef}>
                                    <option value="private">Private</option>
                                    <option value="public">Public</option>
                                </select>
                                <label className="text-danger" ref={privacyErrorLabelRef}></label>
                            </div>
                            <div className="d-flex flex-column w-50 m-1">
                                <label ><b>Capacity:</b></label>
                                <input 
                                    type="number" 
                                    className="p-2" 
                                    min="2" max="14" 
                                    ref={capacityInputRef}>
                                </input>
                                <label className="text-danger" ref={capacityErrorLabelRef}></label>
                            </div>
                        </div>

                        <img className="p-1" src="/limanska_pijaca_mapa.PNG" alt="Mapa Limanske pijace" />

                        <div className="d-flex flex-column m-1">
                            <label ><b>Address:</b></label>
                            <input type="text" className="p-2" ref={addressInputRef}></input>
                            <label className="text-danger" ref={addressErrorLabelRef}></label>
                        </div>

                        <div className="d-flex flex-column m-1">
                            <label ><b>Price:</b></label>
                            <input 
                                type="text" 
                                className="p-2" 
                                placeholder="Price in RSD"
                                ref={priceInputRef}>
                            </input>
                            <label className="text-danger" ref={priceErrorLabelRef}></label>
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
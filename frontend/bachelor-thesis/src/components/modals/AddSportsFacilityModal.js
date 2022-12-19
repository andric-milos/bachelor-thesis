import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import { useRef, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function AddSportsFacilityModal() {
    const [show, setShow] = useState(false);
    const navigate = useNavigate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const nameInputRef = useRef();
    const addressInputRef = useRef();
    const priceInputRef = useRef();

    const submitHandler = () => {
        if (formValidation()) {
            const dto = {
                name: nameInputRef.current.value,
                pricePerHour: parseFloat(priceInputRef.current.value),
                location: {
                    address: addressInputRef.current.value
                }
            }

            // console.log(dto);

            axios.post("http://localhost:8080/sportsFacility", dto, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
                .then(response => {
                    if (response.status == 200) {
                        alert("You successfully added a sports facility!");
                        navigate(0);
                    }
                })
                .catch(error => {
                    console.log(error);
                    alert("An error occurred");
                });
        } else {
            alert("Invalid input of data!");
        }
    }

    const formValidation = () => {
        let hasErrors = 0;

        if (isNaN(parseFloat(priceInputRef.current.value)))
            ++hasErrors;
        if (!nameInputRef.current.value)
            ++hasErrors;
        if (!addressInputRef.current.value)
            ++hasErrors;

        if (hasErrors)
            return false;
        
        return true;
    }

    return (
        <div>
            <Button variant="primary" size="sm" onClick={handleShow}>Add sports facility</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add a sports facility</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column m-1">
                        <div className="d-flex flex-column m-1">
                            <label ><b>Name:</b></label>
                            <input type="text" className="p-2" ref={nameInputRef}></input>
                        </div>

                        <div className="d-flex flex-column m-1">
                            <label ><b>Address:</b></label>
                            <input type="text" className="p-2" ref={addressInputRef}></input>
                        </div>

                        <div className="d-flex flex-column m-1">
                            <label ><b>Price per hour:</b></label>
                            <input
                                type="text"
                                className="p-2"
                                placeholder="Price in RSD"
                                pattern="\d*(.\d{1,2})?"
                                ref={priceInputRef}>
                            </input>
                        </div>
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

export default AddSportsFacilityModal;
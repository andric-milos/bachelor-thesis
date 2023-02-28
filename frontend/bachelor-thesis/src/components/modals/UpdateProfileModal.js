import { useState } from "react";
import { Button, Modal } from "react-bootstrap";

/* Prop "style" only serves for styling the button that initiates the modal dialog! */
function UpdateProfileModal({ style }) {
    const [show, setShow] = useState(false);

    const handleClose = () => {
        setShow(false);
    };

    const handleShow = () => setShow(true);

    return (
        <div>
            <Button variant="primary" onClick={handleShow} style={style}>Update information</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Update information</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column p-1 justify-content-center">
                        <label className="p-2" htmlFor="email"><b>E-mail</b></label>
                        <input type="text" className="p-2" id="email" disabled></input>

                        <label className="p-2" htmlFor="firstname"><b>First name</b></label>
                        <input type="text" className="p-2" id="firstname"></input>

                        <label className="p-2" htmlFor="lastname"><b>Last name</b></label>
                        <input type="text" className="p-2" id="lastname"></input>

                        <label className="p-2" htmlFor="telephone"><b>Telephone</b></label>
                        <input type="text" className="p-2" id="telephone"></input>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary">Update</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default UpdateProfileModal;
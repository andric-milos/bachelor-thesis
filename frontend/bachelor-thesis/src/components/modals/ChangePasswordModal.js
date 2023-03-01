import { useState } from "react";
import { Button, Modal } from "react-bootstrap";

/* Prop "style" only serves for styling the button that initiates the modal dialog! */
function ChangePasswordModal({ style }) {
    const [show, setShow] = useState(false);

    const handleClose = () => {
        setShow(false);
    };

    const handleShow = () => setShow(true);

    return (
        <div>
            <Button variant="primary" onClick={handleShow} style={style}>Change password</Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Change password</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="d-flex flex-column p-1 justify-content-center">
                        <label className="p-2" htmlFor="email"><b>Enter your password</b></label>
                        <input type="password" className="p-2"></input>

                        <label className="p-2" htmlFor="firstname"><b>New password</b></label>
                        <input type="password" className="p-2"></input>

                        <label className="p-2" htmlFor="lastname"><b>Confirm password</b></label>
                        <input type="password" className="p-2"></input>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="primary">Confirm</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default ChangePasswordModal;
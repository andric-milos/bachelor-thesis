import axios from 'axios';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
    const navigate = useNavigate();

    const firstNameInputRef = useRef();
    const lastNameInputRef = useRef();
    const emailInputRef = useRef();
    const telephoneInputRef = useRef();
    const typeOfUserInputRef = useRef();
    const passwordInputRef = useRef();
    const confirmPasswordInputRef = useRef();

    function submitHandler(event) {
        event.preventDefault();

        let registerInfo = {
            "firstName" : firstNameInputRef.current.value,
            "lastName" : lastNameInputRef.current.value,
            "email" : emailInputRef.current.value,
            "password" : passwordInputRef.current.value,
            "telephone" : telephoneInputRef.current.value,
            "role" : typeOfUserInputRef.current.value
        }

        axios.post("http://localhost:8080/user/register", registerInfo)
            .then(response => {
                if (response.status == 200) {
                    alert("You successfully registered!");
                    navigate("/");
                }
            })
            .catch(error => {
                console.log(error);
                alert(error.response.data);
            });
    }

    return (
        <div className="d-flex p-2 mt-5 justify-content-center">
            <form className="d-flex flex-column col-sm-4" onSubmit={submitHandler}>
                <h1 className="p-2 d-flex justify-content-center">Register</h1>

                <label className="p-2" htmlFor="firstname"><b>First name</b></label>
                <input type="text" className="p-2" id="firstname" ref={firstNameInputRef} required></input>

                <label className="p-2" htmlFor="lastname"><b>Last name</b></label>
                <input type="text" className="p-2" id="lastname" ref={lastNameInputRef} required></input>

                <label className="p-2" htmlFor="email"><b>E-mail</b></label>
                <input type="text" className="p-2" id="email" ref={emailInputRef} required></input>

                <label className="p-2" htmlFor="telephone"><b>Telephone</b></label>
                <input type="text" className="p-2" id="telephone" ref={telephoneInputRef} required></input>

                <label className="p-2" htmlFor="typesOfUser"><b>Type of user</b></label>
                <select name="typesOfUser" id="typesOfUser" className="p-2" ref={typeOfUserInputRef} required>
                    <option value="player">Player</option>
                    <option value="manager">Manager</option>
                </select>

                <label className="p-2" htmlFor="password"><b>Password</b></label>
                <input type="password" className="p-2" id="password" placeholder="Password" ref={passwordInputRef}></input>
                
                <label className="p-2" htmlFor="confirmPassword"><b>Confirm password</b></label>
                <input type="password" className="p-2" id="confirmPassword" placeholder="Password" ref={confirmPasswordInputRef}></input>

                <br></br>
                <button type="submit" className="btn btn-primary mb-5">Confirm</button>
            </form>
        </div>
    );
}

export default RegisterPage;
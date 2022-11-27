import axios from 'axios';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const navigate = useNavigate();

    const emailInputRef = useRef();
    const passwordInputRef = useRef();

    function submitHandler(event) {
        event.preventDefault();

        let loginInfo = {
            "email" : emailInputRef.current.value,
            "password" : passwordInputRef.current.value
        };

        axios.post("http://localhost:8080/user/login", loginInfo)
            .then(response => {
                localStorage.setItem("accessToken", response.data.accessToken);
                localStorage.setItem("refreshToekn", response.data.refreshToken);
                localStorage.setItem("subject", response.data.subject);
                localStorage.setItem("issuer", response.data.issuer);
                localStorage.setItem("roles", response.data.roles);
                localStorage.setItem("expiresIn", response.data.expiresIn);

                navigate("/");
            })
            .catch(error => {
                console.log(error);
                alert("An errorr occurred!");
            });
    }

    return (
        <div className="d-flex p-2 mt-5 justify-content-center">
            <form className="d-flex flex-column col-sm-4" onSubmit={submitHandler}>
                <h1 className="p-2 d-flex justify-content-center">Login</h1>

                <label className="p-2" htmlFor="username"><b>E-mail</b></label>
                <input type="text" className="p-2" id="username" ref={emailInputRef} required></input>

                <label className="p-2" htmlFor="password"><b>Password</b></label>
                <input type="password" className="p-2" id="password" placeholder="Password" ref={passwordInputRef} required></input>

                <br></br>
                <button type="submit" className="btn btn-primary">Confirm</button>
            </form>
        </div>
    );
}

export default LoginPage;
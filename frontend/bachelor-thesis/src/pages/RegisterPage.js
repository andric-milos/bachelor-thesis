function RegisterPage() {
    return (
        <div className="d-flex p-2 mt-5 justify-content-center">
            <form className="d-flex flex-column col-sm-4">
                <h1 className="p-2 d-flex justify-content-center">Register</h1>

                <label className="p-2" htmlFor="firstname"><b>First name</b></label>
                <input type="text" className="p-2" id="firstname" required></input>

                <label className="p-2" htmlFor="lastname"><b>Last name</b></label>
                <input type="text" className="p-2" id="lastname" required></input>

                <label className="p-2" htmlFor="email"><b>E-mail</b></label>
                <input type="text" className="p-2" id="email" required></input>

                <label className="p-2" htmlFor="telephone"><b>Telephone</b></label>
                <input type="text" className="p-2" id="telephone" required></input>

                <label className="p-2" htmlFor="typeOfUser"><b>Type of user</b></label>
                <input type="text" className="p-2" id="typeOfUser" required></input>

                <label className="p-2" htmlFor="password"><b>Password</b></label>
                <input type="password" className="p-2" id="password" placeholder="Password"></input>
                
                <label className="p-2" htmlFor="confirmPassword"><b>Confirm password</b></label>
                <input type="password" className="p-2" id="confirmPassword" placeholder="Password"></input>

                <br></br>
                <button type="submit" className="btn btn-primary mb-5">Confirm</button>
            </form>
        </div>
    );
}

export default RegisterPage;
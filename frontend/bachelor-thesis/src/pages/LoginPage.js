function LoginPage() {
    return (
        <div className="d-flex p-2 mt-5 justify-content-center">
            <form className="d-flex flex-column col-sm-4">
                <h1 className="p-2 d-flex justify-content-center">Login</h1>

                <label className="p-2" htmlFor="username"><b>Username</b></label>
                <input type="text" className="p-2" id="username" required></input>

                <label className="p-2" htmlFor="password"><b>Password</b></label>
                <input type="password" className="p-2" id="password" placeholder="Password" required></input>

                <br></br>
                <button type="submit" className="btn btn-primary">Confirm</button>
            </form>
        </div>
    );
}

export default LoginPage;
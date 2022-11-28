function UserInfo() {
    return (
        <div className="d-flex flex-column col-sm-4">
            <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="Profile logo" />

            <label className="p-2"><b>Name: John Doe</b></label>
            <label className="p-2"><b>E-mail: johndoe@gmail.com</b></label>
            <label className="p-2"><b>Telephone: 060123456</b></label>

            <br></br>
            <button type="submit" className="btn btn-primary">Update information</button>
            <br></br>
            <button type="submit" className="btn btn-primary">Change password</button>
        </div>
    );
}

export default UserInfo;
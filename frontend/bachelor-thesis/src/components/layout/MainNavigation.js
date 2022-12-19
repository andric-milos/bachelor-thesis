import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import classes from './MainNavigation.module.css';

function MainNavigation() {
    const navigate = useNavigate();

    function logout() {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToekn");
        localStorage.removeItem("subject");
        localStorage.removeItem("issuer");
        localStorage.removeItem("roles");
        localStorage.removeItem("expiresIn");

        navigate("/login");
    }

    return (
        <header className={classes.header}>
            <nav>
                <ul>
                    <li>
                        <Link to='/'>Homepage</Link>
                    </li>
                    <li>
                        <Link to='/register'>Register</Link>
                    </li>
                    <li>
                        <Link to='/login'>Login</Link>
                    </li>
                    <li>
                        <Link to='/profile'>Profile</Link>
                    </li>
                    <li>
                        <Link to='/facilities'>Facilities</Link>
                    </li>
                    <li>
                        <button type='button' className='btn btn-dark' onClick={logout}>Logout</button>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default MainNavigation;
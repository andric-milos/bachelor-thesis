import axios from 'axios';
import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import classes from './MainNavigation.module.css';

function MainNavigation() {
    const [role, setRole] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        axios.get("http://localhost:8080/user/whoami", { headers: { 'Authorization': 'Bearer ' + localStorage.getItem("accessToken") } })
            .then(response => {
                console.log(response);
                setRole(response.data.roles[0]);
            })
            .catch(error => {
                console.log(error);
                setRole(null);
            });
    }, [location])

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
                    <div className='d-flex flex-row'>
                        <li>
                            <Link to='/'>Homepage</Link>
                        </li>
                        {role == null ?
                            <li>
                                <Link to='/register'>Register</Link>
                            </li> : null
                        }
                        {role == null ?
                            <li>
                                <Link to='/login'>Login</Link>
                            </li> : null
                        }
                        {role != null ?
                            <li>
                                <Link to='/profile'>Profile</Link>
                            </li> : null
                        }
                        <li>
                            <Link to='/facilities'>Facilities</Link>
                        </li>
                    </div>
                    {role != null ?
                        <li>
                            <button type='button' className='btn btn-dark' onClick={logout}>Logout</button>
                        </li> : null
                    }
                </ul>
            </nav>
        </header>
    );
}

export default MainNavigation;
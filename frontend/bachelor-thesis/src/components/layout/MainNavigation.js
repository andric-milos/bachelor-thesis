import { Link } from 'react-router-dom';

import classes from './MainNavigation.module.css';

function MainNavigation() {
    return (
        <header>
            <div className={classes.logo}>React Meetups</div>
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
                </ul>
            </nav>
        </header>
    );
}

export default MainNavigation;
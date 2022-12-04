import { Route, Routes } from 'react-router-dom';

import MainNavigation from './components/layout/MainNavigation';

import Homepage from './pages/Homepage';
import LoginPage from './pages/LoginPage';
import PlayerProfilePage from './pages/PlayerProfilePage';
import RegisterPage from './pages/RegisterPage';
import Forbidden from './pages/Forbidden';
import GroupPage from './pages/GroupPage';

function App() {
  return (
    <div>
      <MainNavigation />
      <Routes>
        <Route path='/' element={ <Homepage /> } />
        <Route path='/register' element={ <RegisterPage /> } />
        <Route path='/login' element={ <LoginPage /> } />
        <Route path='/profile' element={ <PlayerProfilePage /> } />
        <Route path='/forbidden' element={ <Forbidden /> } />
        <Route path='/group' element={ <GroupPage /> } />
      </Routes>
    </div>
  );
}

export default App;

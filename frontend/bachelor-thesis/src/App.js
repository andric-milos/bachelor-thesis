import { Route, Routes } from 'react-router-dom';

import MainNavigation from './components/layout/MainNavigation';

import Homepage from './pages/Homepage';
import LoginPage from './pages/LoginPage';
import PlayerProfilePage from './pages/PlayerProfilePage';
import RegisterPage from './pages/RegisterPage';
import ForbiddenPage from './pages/ForbiddenPage';
import GroupPage from './pages/GroupPage';
import ErrorPage from './pages/ErrorPage';
import AppointmentPage from './pages/AppointmentPage';

function App() {
  return (
    <div>
      <MainNavigation />
      <Routes>
        <Route path='/' element={ <Homepage /> } />
        <Route path='/register' element={ <RegisterPage /> } />
        <Route path='/login' element={ <LoginPage /> } />
        <Route path='/profile' element={ <PlayerProfilePage /> } />
        <Route path='/forbidden' element={ <ForbiddenPage /> } />
        <Route path='/group/:groupId' element={ <GroupPage /> } />
        <Route path='/error' element={ <ErrorPage /> } />
        <Route path='/appointment/:id' element={ <AppointmentPage /> } />
      </Routes>
    </div>
  );
}

export default App;

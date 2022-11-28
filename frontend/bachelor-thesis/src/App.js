import { Route, Routes } from 'react-router-dom';

import MainNavigation from './components/layout/MainNavigation';

import Homepage from './pages/Homepage';
import LoginPage from './pages/LoginPage';
import PlayerProfilePage from './pages/PlayerProfilePage';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <div>
      <MainNavigation />
      <Routes>
        <Route path='/' element={ <Homepage /> } />
        <Route path='/register' element={ <RegisterPage /> } />
        <Route path='/login' element={ <LoginPage /> } />
        <Route path='/profile' element={ <PlayerProfilePage /> } />
      </Routes>
    </div>
  );
}

export default App;

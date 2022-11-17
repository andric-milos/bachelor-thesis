import { Route, Routes } from 'react-router-dom';

import MainNavigation from './components/layout/MainNavigation';

import Homepage from './pages/Homepage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <div>
      <MainNavigation />
      <Routes>
        <Route path='/' element={ <Homepage /> } />
        <Route path='/register' element={ <RegisterPage /> } />
        <Route path='/login' element={ <LoginPage /> } />
      </Routes>
    </div>
  );
}

export default App;

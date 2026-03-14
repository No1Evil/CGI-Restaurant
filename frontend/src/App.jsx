import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import MyReservations from './pages/MyReservations.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import Layout from "./components/Layout.jsx";
import {RestaurantProvider} from "./context/RestaurantContext.jsx";
import SelectRestaurant from "./pages/SelectRestaurant.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";

function App() {
    return (
        <AuthProvider>
            <RestaurantProvider>
                <Router>
                    <Layout>
                        <Routes>
                            <Route path="/" element={<Dashboard />} />
                            <Route path="/login" element={<LoginPage />} />
                            <Route path="/register" element={<RegisterPage />} />
                            <Route path="/select-restaurant" element={<SelectRestaurant />} />
                            <Route path="/reservations" element={
                                <ProtectedRoute>
                                    <MyReservations />
                                </ProtectedRoute>
                            } />
                        </Routes>
                    </Layout>
                </Router>
            </RestaurantProvider>
        </AuthProvider>
    );
}

export default App;
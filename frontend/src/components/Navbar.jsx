import { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import {RestaurantContext} from "../context/RestaurantContext.jsx";
import UserMenu from "./UserMenu.jsx";

const Navbar = () => {
    const { user, logout } = useContext(AuthContext);
    const { selectedRestaurant } = useContext(RestaurantContext);

    return (
        <nav className="flex items-center gap-5 p-4 bg-sky-900 text-white">
            <Link to="/" className="hover:text-emerald-300">Homepage</Link>

            <Link to="/reservations" className="hover:text-emerald-300">My reservations</Link>

            <Link to="/select-restaurant" className="hover:text-emerald-300">
                {selectedRestaurant ? `Restaurant: ${selectedRestaurant.name}` : "Select restaurant"}
            </Link>

            <div className="ml-auto hover:text-emerald-300">
                <UserMenu />
            </div>
        </nav>
    );
};
export default Navbar;
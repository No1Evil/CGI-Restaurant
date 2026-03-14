import { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const UserMenu = () => {
    const { user, logout } = useContext(AuthContext);
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className="relative">
            <button
                onClick={() => setIsOpen(!isOpen)}
                className="px-4 py-2 bg-sky-800 hover:bg-sky-700 text-white rounded transition"
            >
                {user ? `${user.firstName} ${user.secondName}` : "Login / Register"}
            </button>

            {isOpen && (
                <div className="absolute right-0 mt-2 w-48 bg-white text-gray-800 rounded-lg shadow-xl border border-gray-100 z-50 p-4">
                    {user ? (
                        <div className="flex flex-col gap-2">
                            <p className="text-xs text-gray-500 truncate">{user.email}</p>

                            {user.globalRole === 'ADMIN' && (
                                <Link to="/admin" className="text-red-600 font-bold hover:text-red-800">
                                    Admin Panel
                                </Link>
                            )}

                            <div className="border-t my-1" />

                            <button
                                onClick={logout}
                                className="text-left text-sm hover:text-sky-700"
                            >
                                Logout
                            </button>
                        </div>
                    ) : (
                        <div className="flex flex-col gap-2">
                            <Link to="/login" className="hover:text-sky-700">Login</Link>
                            <Link to="/register" className="hover:text-sky-700">Register</Link>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default UserMenu;
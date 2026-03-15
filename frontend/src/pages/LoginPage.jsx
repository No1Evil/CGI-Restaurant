import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { authService } from '../api/authService';

// TODO MAKE SHARED WITH REGISTER PAGE
const LoginPage = () => {
    const { login } = useContext(AuthContext);

    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const data = await authService.login(email, password);
            login(data.user, data.token);
            navigate('/');
        } catch (error) {
            alert("Invalid login");
        }
    };

    return (
        <div className="flex flex-col items-center self-center mx-auto p-4">
            <h1 className="text-2xl font-bold mb-6">Login form</h1>

            <form onSubmit={handleLogin} className="w-full max-w-sm p-6 bg-white rounded-lg shadow-md border border-gray-200">
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-sky-500 outline-none"
                    />
                </div>

                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-sky-500 outline-none"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-sky-900 text-white py-2 rounded hover:bg-sky-800 transition duration-200"
                >
                    Login
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
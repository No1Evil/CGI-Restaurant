import React, {useContext, useEffect, useState} from 'react';
import { AuthContext } from '../context/AuthContext';
import { Navigate } from 'react-router-dom';
import { reservationService } from '../api/reservationService';

const AdminPanel = () => {
    const { user } = useContext(AuthContext);
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);

    if (!user || user.role !== 'ADMIN') {
        return <Navigate to="/" replace />;
    }

    useEffect(() => {
        loadAllReservations();
    }, []);

    const loadAllReservations = async () => {
        try {
            setLoading(true);
            const data = await reservationService.getAll();
            setReservations(data);
        } catch (error) {
            console.error("Failed to fetch reservations:", error);
            alert("Error loading reservations");
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = async (id) => {
        if (!window.confirm("Are you sure you want to cancel this reservation?")) return;
        try {
            await reservationService.cancel(id);
            setReservations(prev => prev.filter(r => r.id !== id));
        } catch (error) {
            alert("Failed to cancel reservation");
        }
    };

    if (loading) return <div className="p-6">Loading all reservations...</div>;

    return (
        <div className="p-6 max-w-6xl mx-auto">
            <h1 className="text-3xl font-bold mb-6">Admin Panel: All Reservations</h1>

            <div className="bg-white shadow rounded-lg overflow-hidden">
                <table className="min-w-full leading-normal">
                    <thead>
                    <tr className="bg-gray-100 border-b">
                        <th className="px-5 py-3 text-left text-xs font-semibold text-gray-600 uppercase">ID</th>
                        <th className="px-5 py-3 text-left text-xs font-semibold text-gray-600 uppercase">User ID</th>
                        <th className="px-5 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Table</th>
                        <th className="px-5 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Time</th>
                        <th className="px-5 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Status</th>
                        <th className="px-5 py-3"></th>
                    </tr>
                    </thead>
                    <tbody>
                    {reservations.map((res) => {
                        const start = new Date(res.reservationStart);
                        const end = new Date(res.reservationEnd);
                        const isExpired = end < new Date();

                        return (
                            <tr key={res.id} className="border-b hover:bg-gray-50">
                                <td className="px-5 py-4 text-sm">{res.id}</td>
                                <td className="px-5 py-4 text-sm">{res.userId}</td>
                                <td className="px-5 py-4 text-sm">Table #{res.tableId}</td>
                                <td className="px-5 py-4 text-sm">
                                    {start.toLocaleDateString()} <br/>
                                    {start.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})} -
                                    {end.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                                </td>
                                <td className="px-5 py-4 text-sm">
                                        <span className={`px-2 py-1 rounded text-xs ${isExpired ? 'bg-gray-200 text-gray-600' : 'bg-green-100 text-green-700'}`}>
                                            {isExpired ? 'Past' : 'Upcoming'}
                                        </span>
                                </td>
                                <td className="px-5 py-4 text-right">
                                    {!isExpired && (
                                        <button
                                            onClick={() => handleCancel(res.id)}
                                            className="text-red-600 hover:text-red-900 font-bold"
                                        >
                                            Cancel
                                        </button>
                                    )}
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>
                {reservations.length === 0 && (
                    <p className="p-10 text-center text-gray-500">No reservations found.</p>
                )}
            </div>
        </div>
    );
};

export default AdminPanel;
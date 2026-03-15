import {useContext, useEffect, useState} from 'react';
import { reservationService } from '../api/reservationService.js';
import { tableService } from '../api/tableService.js';
import {RestaurantContext} from "../context/RestaurantContext.jsx";

const getTodayDateString = () => {
    const today = new Date();
    return today.toISOString().split('T')[0];
};

const generateTimeSlots = (startHour = 10, endHour = 22) => {
    const slots = [];
    for (let i = startHour; i <= endHour; i++) {
        slots.push(`${String(i).padStart(2, '0')}:00`);
    }
    return slots;
};

const MyReservations = () => {
    const [reservations, setReservations] = useState([]);

    const { selectedRestaurant } = useContext(RestaurantContext);

    const timeSlots = generateTimeSlots();

    const [searchParams, setSearchParams] = useState({
        date: getTodayDateString(),
        startTime: '18:00',
        duration: 2,
        capacity: 2,
        restaurantId: selectedRestaurant.id,
        zoneId: 1
    });

    const [availableTables, setAvailableTables] = useState([]);
    const [selectedTable, setSelectedTable] = useState(null);
    const [hoveredTable, setHoveredTable] = useState(null);

    useEffect(() => {
        loadReservations();
    }, []);

    const loadReservations = () => {
        reservationService.getAll().then(setReservations).catch(console.error);
    };

    const handleCancel = async (id) => {
        try {
            await reservationService.cancel(id);
            setReservations(prev => prev.filter(r => r.id !== id));
        } catch (error) {
            console.error("Error on discarding:", error);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const start = new Date(`${searchParams.date}T${searchParams.startTime}:00`);
            const end = new Date(start.getTime() + searchParams.duration * 60 * 60 * 1000);

            const request = {
                restaurantId: searchParams.restaurantId,
                zoneId: searchParams.zoneId,
                capacity: parseInt(searchParams.capacity),
                startTime: start.toISOString().split('.')[0] + "Z",
                endTime: end.toISOString().split('.')[0] + "Z"
            };
            const tables = await tableService.getAvailable(request);
            setAvailableTables(tables);
            setSelectedTable(null);
        } catch (error) {
            console.error("Error in searching for tables:", error);
        }
    };

    const handleReserve = async () => {
        if (!selectedTable) return;

        try {
            const start = new Date(`${searchParams.date}T${searchParams.startTime}:00`);
            const end = new Date(start.getTime() + searchParams.duration * 60 * 60 * 1000);

            const request = {
                tableId: selectedTable.tableId,
                start: start.toISOString().split('.')[0] + "Z",
                end: end.toISOString().split('.')[0] + "Z"
            };

            await reservationService.create(request);
        } catch (error) {
            console.error("Error in reservation:", error);
        }
    };

    return (
        <div className="p-6 max-w-6xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-8">

            <div>
                <h1 className="text-2xl font-bold mb-4">Reserve a table</h1>

                <form onSubmit={handleSearch} className="bg-white p-4 rounded-lg shadow-sm mb-6 grid grid-cols-2 gap-4">
                    <div>
                        <label className="block text-sm text-gray-600">Date</label>
                        <input
                            type="date"
                            required
                            min={getTodayDateString()}
                            className="w-full border p-2 rounded"
                            value={searchParams.date}
                            onChange={e => setSearchParams({...searchParams, date: e.target.value})}
                        />
                    </div>
                    <div>
                        <label className="block text-sm text-gray-600">Person count</label>
                        <input
                            type="number"
                            min="1"
                            required
                            className="w-full border p-2 rounded"
                            value={searchParams.capacity}
                            onChange={e => setSearchParams({...searchParams, capacity: e.target.value})}
                        />
                    </div>
                    <div>
                        <label className="block text-sm text-gray-600">Start time</label>
                        <select
                            className="w-full border p-2 rounded bg-white"
                            value={searchParams.startTime}
                            onChange={e => setSearchParams({...searchParams, startTime: e.target.value})}
                        >
                            {timeSlots.map(time => (
                                <option key={time} value={time}>{time}</option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label className="block text-sm text-gray-600">Duration</label>
                        <select
                            className="w-full border p-2 rounded bg-white"
                            value={searchParams.duration}
                            onChange={e => setSearchParams({...searchParams, duration: parseInt(e.target.value)})}
                        >
                            <option value={1}>1 hour</option>
                            <option value={2}>2 hours</option>
                            <option value={3}>3 hours</option>
                            <option value={4}>4 hours</option>
                            <option value={5}>5 hours</option>
                        </select>
                    </div>
                    <button type="submit" className="col-span-2 bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition-colors">
                        Find tables
                    </button>
                </form>

                {availableTables.length > 0 && (
                    <div className="bg-white p-4 rounded-lg shadow-sm relative border">
                        <h2 className="font-semibold mb-2">Choose a table on a scheme:</h2>

                        {hoveredTable && (
                            <div className="absolute top-2 right-2 bg-gray-800 text-white text-sm p-2 rounded pointer-events-none z-10">
                                Table #{hoveredTable.tableId} <br/>
                                People capacity: {hoveredTable.capacity} <br/>
                                Zone name: {"todo"}
                            </div>
                        )}

                        <svg viewBox="0 0 800 600" className="w-full h-auto bg-gray-50 border rounded cursor-crosshair">
                            {availableTables.map(table => (
                                <g key={table.tableId}
                                   transform={`translate(${table.posX || table.x}, ${table.posY || table.y})`}
                                   onClick={() => setSelectedTable(table)}
                                   onMouseEnter={() => setHoveredTable(table)}
                                   onMouseLeave={() => setHoveredTable(null)}
                                >
                                    <circle
                                        r="20"
                                        fill={selectedTable?.tableId === table.tableId ? "#22c55e" : "#3b82f6"}
                                        stroke="#1e40af"
                                        strokeWidth="2"
                                    />
                                    <text textAnchor="middle" dy=".3em" fill="white" fontSize="12" className="pointer-events-none">
                                        {table.tableId}
                                    </text>
                                </g>
                            ))}
                        </svg>

                        {selectedTable && (
                            <div className="mt-4 flex justify-between items-center bg-green-50 p-3 rounded border border-green-200">
                                <span>Selected table: <b>#{selectedTable.tableId}</b> with capacity: {searchParams.duration}</span>
                                <button onClick={handleReserve} className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition-colors">
                                    Confirm your reservation
                                </button>
                            </div>
                        )}
                    </div>
                )}
            </div>

            <div>
                <h1 className="text-2xl font-bold mb-4">My reservations</h1>
                <div className="grid gap-4">
                    {reservations.length === 0 ? (
                        <p className="text-gray-500 bg-gray-50 p-4 rounded text-center border">You don`t have any active reservations.</p>
                    ) : (
                        reservations.map((res) => {
                            const startDate = new Date(res.reservationStart);
                            const endDate = new Date(res.reservationEnd);

                            return (
                                <div key={res.id} className="p-4 border rounded-lg flex flex-col sm:flex-row justify-between items-start sm:items-center bg-white shadow-sm hover:shadow-md transition-shadow gap-4">
                                    <div>
                                        <p className="font-bold text-lg text-gray-800">Table #{res.tableId}</p>
                                        <p className="text-sm text-gray-600 flex flex-col">
                                            <span>📅 {startDate.toLocaleDateString()}</span>
                                            <span>🕒 {startDate.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})} - {endDate.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}</span>
                                        </p>
                                    </div>
                                    <button
                                        onClick={() => handleCancel(res.id)}
                                        className="bg-red-50 text-red-600 border border-red-200 px-4 py-2 rounded hover:bg-red-500 hover:text-white transition-colors w-full sm:w-auto"
                                    >
                                        Discard
                                    </button>
                                </div>
                            );
                        })
                    )}
                </div>
            </div>

        </div>
    );
};

export default MyReservations;
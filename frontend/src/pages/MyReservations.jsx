import React, { useContext, useEffect, useState } from 'react';
import { reservationService } from '../api/reservationService.js';
import { tableService } from '../api/tableService.js';
import { zoneService } from "../api/zoneService.js";
import { RestaurantContext } from "../context/RestaurantContext.jsx";
import { getTodayDateString } from '../api/utils.js';
import ReservationForm from '../components/reservation/ReservationForm.jsx';
import ReservationList from '../components/reservation/ReservationList.jsx';

const MyReservations = () => {
    const { selectedRestaurant, loading } = useContext(RestaurantContext);

    const [reservations, setReservations] = useState([]);
    const [zones, setZones] = useState([]);
    const [availableTables, setAvailableTables] = useState([]);
    const [selectedTable, setSelectedTable] = useState(null);
    const [hoveredTable, setHoveredTable] = useState(null);

    const [searchParams, setSearchParams] = useState({
        date: getTodayDateString(),
        startTime: '12:00',
        duration: 1,
        capacity: null,
        zoneId: null
    });

    useEffect(() => {
        loadReservations();
    }, []);

    useEffect(() => {
        if (selectedRestaurant?.id) {
            zoneService.getAll().then(data => {
                setZones(data);
            }).catch(console.error);
        }
    }, [selectedRestaurant]);

    const loadReservations = () => {
        reservationService.getAll().then(setReservations).catch(console.error);
    };

    const getZoneName = (zoneId) => {
        if (!zoneId) return "Any Zone";
        const zone = zones.find(z => z.zoneId?.toString() === zoneId?.toString());
        return zone ? zone.name : "Unknown Zone";
    };

    if (loading) return <div>Загрузка данных ресторана...</div>;
    if (!selectedRestaurant) return <div>Рестораны не найдены.</div>;

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
            if (!selectedRestaurant?.id) {
                alert("Please, firstly choose the restaurant.");
                return;
            }

            const start = new Date(`${searchParams.date}T${searchParams.startTime}:00`);
            const end = new Date(start.getTime() + searchParams.duration * 60 * 60 * 1000);

            const request = {
                restaurantId: selectedRestaurant.id,
                zoneId: searchParams.zoneId,
                capacity: searchParams.capacity,
                startTime: start.toISOString().split('.')[0] + "Z",
                endTime: end.toISOString().split('.')[0] + "Z"
            };

            const tables = await tableService.getAvailable(request);
            if (tables.length < 1) {
                alert("There are no available tables at that time!");
            }
            setAvailableTables(tables);
            setSelectedTable(null);
        } catch (error) {
            alert(error?.response?.data || "Search error");
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
            loadReservations();

            setSelectedTable(null);
            await handleSearch({ preventDefault: () => {} });

            alert("Reservation successful!");
        } catch (error) {
            alert(error?.response?.data || "Reservation error");
            console.error("Error in reservation:", error);
        }
    };

    return (
        <div className="p-6 max-w-6xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div>
                <h1 className="text-2xl font-bold mb-4">Reserve a table</h1>

                <ReservationForm
                    searchParams={searchParams}
                    setSearchParams={setSearchParams}
                    handleSearch={handleSearch}
                    zones={zones}
                />

                {availableTables.length > 0 && (
                    <div className="bg-white p-4 rounded-lg shadow-sm relative border">
                        <h2 className="font-semibold mb-2">Choose a table on a scheme:</h2>

                        {hoveredTable && (
                            <div className="absolute top-2 right-2 bg-gray-800 text-white text-sm p-2 rounded pointer-events-none z-10">
                                Table #{hoveredTable.tableId} <br/>
                                Capacity: {hoveredTable.capacity} <br/>
                                {/* Если стол сам знает свою зону с бэкенда - лучше брать оттуда */}
                                Zone: {getZoneName(hoveredTable.zoneId || searchParams.zoneId)}
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
                                <span>Selected table: <b>#{selectedTable.tableId}</b> (Capacity: {selectedTable.capacity})</span>
                                <button onClick={handleReserve} className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition-colors">
                                    Confirm reservation
                                </button>
                            </div>
                        )}
                    </div>
                )}
            </div>

            <ReservationList
                reservations={reservations}
                handleCancel={handleCancel}
            />
        </div>
    );
};

export default MyReservations;
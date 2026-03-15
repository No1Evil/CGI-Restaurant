import React from 'react';
import { getTodayDateString, generateTimeSlots } from '../../api/utils.js';

const ReservationForm = ({ searchParams, setSearchParams, handleSearch, zones }) => {
    const timeSlots = generateTimeSlots();

    const updateParam = (key, value, isNumber = false) => {
        let finalValue = value;
        if (value === "any" || value === "") {
            finalValue = null;
        } else if (isNumber && value !== null) {
            finalValue = parseInt(value, 10);
        }
        setSearchParams(prev => ({ ...prev, [key]: finalValue }));
    };

    return (
        <form onSubmit={handleSearch} className="bg-white p-4 rounded-lg shadow-sm mb-6 grid grid-cols-2 gap-4">
            <div>
                <label className="block text-sm text-gray-600">Date</label>
                <input
                    type="date"
                    required
                    min={getTodayDateString()}
                    className="w-full border p-2 rounded"
                    value={searchParams.date || ""}
                    onChange={e => updateParam('date', e.target.value)}
                />
            </div>

            <div>
                <label className="block text-sm text-gray-600">Person count</label>
                <select
                    className="w-full border p-2 rounded bg-white"
                    value={searchParams.capacity === null ? "any" : searchParams.capacity}
                    onChange={e => updateParam('capacity', e.target.value, true)}
                >
                    <option value="any">Any</option>
                    {[1, 2, 3, 4, 5, 6, 7, 8].map(num => (
                        <option key={num} value={num}>{num} {num === 1 ? 'person' : 'people'}</option>
                    ))}
                </select>
            </div>

            <div>
                <label className="block text-sm text-gray-600">Start time</label>
                <select
                    className="w-full border p-2 rounded bg-white"
                    value={searchParams.startTime}
                    onChange={e => updateParam('startTime', e.target.value)}
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
                    onChange={e => updateParam('duration', e.target.value, true)}
                >
                    {[1, 2, 3, 4, 5].map(num => (
                        <option key={num} value={num}>{num} {num === 1 ? 'hour' : 'hours'}</option>
                    ))}
                </select>
            </div>

            <div className="col-span-2">
                <label className="block text-sm text-gray-600">Restaurant Zone</label>
                <select
                    className="w-full border p-2 rounded bg-white"
                    value={searchParams.zoneId === null ? "any" : searchParams.zoneId}
                    onChange={e => updateParam('zoneId', e.target.value, true)}
                >
                    <option value="any">Any (All zones)</option>
                    {zones.map(zone => (
                        <option key={zone.zoneId} value={zone.zoneId}>{zone.name}</option>
                    ))}
                </select>
            </div>

            <button type="submit" className="col-span-2 bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition-colors">
                Find tables
            </button>
        </form>
    );
};

export default ReservationForm;
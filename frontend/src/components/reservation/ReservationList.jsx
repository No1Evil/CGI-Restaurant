import React from 'react';

const ReservationList = ({ reservations, handleCancel }) => {
    return (
        <div>
            <h1 className="text-2xl font-bold mb-4">My reservations</h1>
            <div className="grid gap-4">
                {reservations.length === 0 ? (
                    <p className="text-gray-500 bg-gray-50 p-4 rounded text-center border">
                        You don't have any active reservations.
                    </p>
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
                                        <span>
                                            🕒 {startDate.toLocaleTimeString([], {
                                                hour: '2-digit',
                                                minute: '2-digit',
                                                hour12: true
                                            })} - {endDate.toLocaleTimeString([], {
                                                hour: '2-digit',
                                                minute: '2-digit',
                                                hour12: true
                                            })}
                                        </span>
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
    );
};

export default ReservationList;
import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { RestaurantContext } from '../context/RestaurantContext';

const SelectRestaurant = () => {
    const { restaurants, updateRestaurant } = useContext(RestaurantContext);
    const navigate = useNavigate();

    const handleSelect = (id) => {
        updateRestaurant(id);
        navigate('/');
    };

    return (
        <div className="self-center mx-auto">
            <h1 className="text-3xl font-bold mb-8 text-gray-800">Select a restaurant</h1>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {restaurants.map((r) => (
                    <div key={r.id} className="border border-gray-200 p-6 rounded-xl shadow-sm hover:shadow-md transition-shadow bg-white">
                        <h3 className="text-xl font-semibold text-sky-900 mb-3">{r.name}</h3>
                        <div className="text-sm text-gray-600 space-y-1">
                            <p><span className="font-bold">Address:</span> {r.address}</p>
                            <p><span className="font-bold">Open time:</span> {r.openTime} - {r.closeTime}</p>
                            <p><span className="font-bold">Phone:</span> {r.phone}</p>
                            <p><span className="font-bold">Email:</span> {r.email}</p>
                        </div>

                        <button
                            onClick={() => handleSelect(r.id)}
                            className="mt-6 w-full py-2 bg-sky-900 text-white rounded-lg hover:bg-sky-800 transition font-medium"
                        >
                            Select restaurant
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};
export default SelectRestaurant;
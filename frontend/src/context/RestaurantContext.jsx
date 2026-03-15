import { createContext, useState, useEffect } from 'react';
import api from '../api/axiosConfig.js';

export const RestaurantContext = createContext(undefined);

export const RestaurantProvider = ({ children }) => {
    const [restaurants, setRestaurants] = useState([]);
    const [selectedRestaurant, setSelectedRestaurant] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.post('/restaurant/all').then(res => {
            const list = res.data;
            setRestaurants(list);

            const saved = localStorage.getItem('selectedRestaurantId');
            if (saved) {
                setSelectedRestaurant(list.find(r => r.id.toString() === saved) || list[0]);
            } else if (list.length > 0) {
                setSelectedRestaurant(list[0]);
            }
        }).finally(() => setLoading(false));
    }, []);

    const updateRestaurant = (id) => {
        const found = restaurants.find(r => r.id.toString() === id.toString());
        setSelectedRestaurant(found);
        localStorage.setItem('selectedRestaurantId', id);
    };

    return (
        <RestaurantContext.Provider value={{ restaurants, selectedRestaurant, updateRestaurant, loading }}>
            {children}
        </RestaurantContext.Provider>
    );
};
import api from './axiosConfig';
import axios from "./axiosConfig.js";

export const reservationService = {
    getAll: async () => (await api.get('/reservation/all')).data,
    create: async (data) => {
        const response = await axios.post('/reservation/create', data, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });

        return response.data
    },
    cancel: async (id) => (await api.post(`/reservation/cancel?reservationId=${id}`)).data,
};
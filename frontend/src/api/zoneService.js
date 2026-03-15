import axios from "./axiosConfig.js";

export const zoneService = {
    getAll: async () => {
        const response = await axios.post('/zone/all', null);
        return response.data;
    }
};
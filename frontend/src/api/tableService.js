import axios from "./axiosConfig.js";

export const tableService = {
    getAvailable: async (dto) => {
        const response = await axios.post('/table/available', dto, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });
        return response.data;
    }
};
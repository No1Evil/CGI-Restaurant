import api from './axiosConfig.js';

export const authService = {
    login: async (email, password) => {
        const params = new URLSearchParams();
        params.append('email', email);
        params.append('password', password);

        const response = await api.post('/auth/login', params, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
        return response.data;
    },

    register: async (userData) => {
        const response = await api.post('/auth/register', userData);
        return response.data;
    },

    getCurrentUser: async (token) => {
        const response = await api.get('/auth/me', {
            headers: { Authorization: `Bearer ${token}` }
        });
        return response.data;
    }
};
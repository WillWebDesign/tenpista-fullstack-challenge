import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
});

export const getTransactions = (page = 0, size = 10) =>
  api.get('/transactions', {
    params: { page, size },
  });

export const createTransaction = (data) => api.post('/transactions', data);

export const deleteTransaction = (id) => api.delete(`/transactions/${id}`);

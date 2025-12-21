import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
});

export const getTransactions = () => api.get('/transactions');

export const createTransaction = (data) => api.post('/transactions', data);

export const deleteTransaction = (id) => api.delete(`/transactions/${id}`);

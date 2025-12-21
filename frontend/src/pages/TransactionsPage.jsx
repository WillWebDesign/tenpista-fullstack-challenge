import { useCallback, useEffect, useState } from 'react';
import { getTransactions, createTransaction, deleteTransaction } from '../api/transactionsApi';
import TransactionForm from '../components/TransactionForm';
import TransactionList from '../components/TransactionList';
import Toast from '../components/Toast';
import GlobalLoading from '../components/GlobalLoading';
import TransactionListSkeleton from '../components/TransactionListSkeleton';

export default function TransactionsPage() {
  const [transactions, setTransactions] = useState([]);
  const [toast, setToast] = useState(null);
  const [initialLoading, setInitialLoading] = useState(true);
  const [actionLoading, setActionLoading] = useState(false);

  const loadTransactions = useCallback(async (silent = false) => {
    try {
      if (!silent) setInitialLoading(true);

      const res = await getTransactions();
      setTransactions(res.data);
    } catch {
      setToast({
        message: 'Error cargando transacciones',
        type: 'error',
      });
    } finally {
      if (!silent) setInitialLoading(false);
    }
  }, []);

  useEffect(() => {
    loadTransactions();
  }, [loadTransactions]);
  const handleCreate = async (data) => {
    try {
      setActionLoading(true);
      await createTransaction(data);
      await loadTransactions(true);

      setToast({
        message: 'Transacción registrada correctamente',
        type: 'success',
      });
    } catch (e) {
      setToast({
        message: e.response?.data?.message || 'Error al crear transacción',
        type: 'error',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      setActionLoading(true);
      await deleteTransaction(id);
      await loadTransactions(true);

      setToast({
        message: 'Transacción eliminada',
        type: 'success',
      });
    } catch {
      setToast({
        message: 'Error al eliminar transacción',
        type: 'error',
      });
    } finally {
      setActionLoading(false);
    }
  };

  return (
    <div className='min-h-screen bg-gray-50'>
      <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8'>
        <header className='mb-8'>
          <h1 className='text-2xl font-semibold text-gray-900'>Tenpista Transactions</h1>
          <p className='text-sm text-gray-600'>Gestión de transacciones de Tenpo</p>
        </header>

        <div className='grid grid-cols-1 md:grid-cols-3 gap-6'>
          <div className='md:col-span-2'>
            {initialLoading ? (
              <TransactionListSkeleton />
            ) : (
              <TransactionList transactions={transactions} onDelete={handleDelete} />
            )}
          </div>

          <div>
            <TransactionForm onSubmit={handleCreate} />
          </div>
        </div>

        {toast && (
          <Toast message={toast.message} type={toast.type} onClose={() => setToast(null)} />
        )}

        {actionLoading && <GlobalLoading />}
      </div>
    </div>
  );
}

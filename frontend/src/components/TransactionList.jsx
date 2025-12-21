import { formatDate } from '../utils/date';

export default function TransactionList({ transactions, onDelete }) {
  return (
    <div
      className='
      bg-[rgb(var(--color-surface))]
      rounded-2xl
      shadow-sm
      border border-gray-100
      p-6
    '
    >
      <div className='flex items-center justify-between mb-4'>
        <h2 className='text-lg font-semibold text-gray-900'>Transacciones</h2>
        <span className='text-sm text-gray-500'>{transactions.length}</span>
      </div>

      {transactions.length === 0 && (
        <div className='py-12 text-center'>
          <p className='text-sm text-gray-500'>Aún no hay transacciones registradas</p>
          <p className='text-xs text-gray-400 mt-1'>Comienza agregando una nueva transacción</p>
        </div>
      )}

      {transactions.length > 0 && (
        <div className='overflow-x-auto'>
          <table className='w-full text-xs sm:text-sm'>
            <thead>
              <tr className='text-left text-gray-500 border-b'>
                <th className='py-2 font-medium'>Comercio</th>
                <th className='font-medium hidden md:table-cell'>Fecha</th>
                <th className='font-medium'>Monto</th>
                <th className='font-medium'>Tenpista</th>
                <th className='text-right'></th>
              </tr>
            </thead>

            <tbody>
              {transactions.map((t) => (
                <tr
                  key={t.id}
                  className='
                    border-b last:border-0
                    hover:bg-gray-50
                    transition-colors
                  '
                >
                  <td className='py-3 px-2 text-gray-900'>
                    <div className='font-medium'>{t.merchant}</div>
                    <div className='text-xs text-gray-500 md:hidden'>
                      {formatDate(t.transactionDate)}
                    </div>
                  </td>

                  <td className='text-sm text-gray-500 hidden md:table-cell'>{formatDate(t.transactionDate)}</td>

                  <td className='font-medium text-gray-900'>${t.amount.toLocaleString()}</td>

                  <td className='text-gray-700'>{t.tenpistaName}</td>

                  <td className='text-right px-2'>
                    <button
                      onClick={() => onDelete(t.id)}
                      className='
                        text-xs
                        font-medium
                        text-[rgb(var(--color-danger))]
                        hover:underline
                        focus:outline-none
                        focus:ring-2
                        focus:ring-[rgb(var(--color-danger))]
                        focus:ring-offset-2
                        rounded
                      '
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

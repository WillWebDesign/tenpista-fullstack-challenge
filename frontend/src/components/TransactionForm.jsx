import { useState } from 'react';

export default function TransactionForm({ onSubmit }) {
  const [form, setForm] = useState({
    amount: '',
    merchant: '',
    tenpistaName: '',
    transactionDate: '',
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: null });
  };

  const validate = () => {
    const newErrors = {};

    if (!form.amount || Number(form.amount) <= 0) {
      newErrors.amount = 'El monto debe ser mayor a 0';
    }

    if (!form.merchant) {
      newErrors.merchant = 'El comercio es obligatorio';
    }

    if (!form.tenpistaName) {
      newErrors.tenpistaName = 'El nombre del Tenpista es obligatorio';
    }

    if (!form.transactionDate) {
      newErrors.transactionDate = 'La fecha es obligatoria';
    } else if (new Date(form.transactionDate) > new Date()) {
      newErrors.transactionDate = 'La fecha no puede ser futura';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) return;

    onSubmit({
      ...form,
      amount: Number(form.amount),
    });

    setForm({
      amount: '',
      merchant: '',
      tenpistaName: '',
      transactionDate: '',
    });
  };

  const inputBase = `
    w-full
    rounded-lg
    border
    px-3
    py-2.5
    text-sm
    focus:outline-none
    focus:ring-2
    focus:ring-[rgb(var(--color-primary))]
    transition
  `;

  const inputError = 'border-red-500 focus:ring-red-500';

  return (
    <form
      onSubmit={handleSubmit}
      className="
        bg-[rgb(var(--color-surface))]
        rounded-2xl
        shadow-sm
        border border-gray-100
        p-6
        space-y-4
      "
    >
      <h2 className="text-lg font-semibold text-gray-900">
        Nueva transacción
      </h2>

      {/* Monto */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Monto
        </label>
        <input
          name="amount"
          type="number"
          value={form.amount}
          onChange={handleChange}
          className={`${inputBase} ${errors.amount ? inputError : 'border-gray-300'}`}
        />
        {errors.amount && (
          <p className="text-xs text-red-600 mt-1">{errors.amount}</p>
        )}
      </div>

      {/* Comercio */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Comercio
        </label>
        <input
          name="merchant"
          value={form.merchant}
          onChange={handleChange}
          className={`${inputBase} ${errors.merchant ? inputError : 'border-gray-300'}`}
        />
        {errors.merchant && (
          <p className="text-xs text-red-600 mt-1">{errors.merchant}</p>
        )}
      </div>

      {/* Tenpista */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Tenpista
        </label>
        <input
          name="tenpistaName"
          value={form.tenpistaName}
          onChange={handleChange}
          className={`${inputBase} ${errors.tenpistaName ? inputError : 'border-gray-300'}`}
        />
        {errors.tenpistaName && (
          <p className="text-xs text-red-600 mt-1">{errors.tenpistaName}</p>
        )}
      </div>

      {/* Fecha */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Fecha de transacción
        </label>
        <input
          name="transactionDate"
          type="datetime-local"
          value={form.transactionDate}
          onChange={handleChange}
          className={`${inputBase} ${errors.transactionDate ? inputError : 'border-gray-300'}`}
        />
        {errors.transactionDate && (
          <p className="text-xs text-red-600 mt-1">{errors.transactionDate}</p>
        )}
      </div>

      <button
        type="submit"
        className="
          w-full
          bg-[rgb(var(--color-primary))]
          text-white
          py-2.5
          rounded-lg
          font-medium
          hover:bg-[rgb(var(--color-primary-hover))]
          transition
        "
      >
        Registrar transacción
      </button>
    </form>
  );
}

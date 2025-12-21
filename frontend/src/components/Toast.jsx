import { useEffect } from 'react';

export default function Toast({ message, type = 'success', onClose }) {
  const variants = {
    success: 'bg-green-600',
    error: 'bg-red-600',
  };

  useEffect(() => {
    const timer = setTimeout(onClose, 3000);
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <div
      className={`
        fixed
        top-6
        right-6
        z-50
        text-white
        px-4
        py-3
        rounded-lg
        shadow-lg
        transition
        ${variants[type]}
      `}
    >
      <div className='flex items-center gap-3'>
        <span className='text-sm font-medium'>{message}</span>
        <button onClick={onClose} className='text-white/80 hover:text-white'>
          ✕
        </button>
      </div>
    </div>
  );
}

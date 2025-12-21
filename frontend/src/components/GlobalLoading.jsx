export default function GlobalLoading() {
  return (
    <div className='fixed inset-0 z-40 bg-black/10 flex items-center justify-center'>
      <div className='bg-white px-4 py-3 rounded-lg shadow-lg text-sm font-medium'>Procesando…</div>
    </div>
  );
}

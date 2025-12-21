export default function TransactionListSkeleton() {
  return (
    <div className='bg-white rounded-xl shadow-md p-6 animate-pulse'>
      <div className='h-5 w-40 bg-gray-200 rounded mb-6' />

      {[1, 2, 3, 4].map((i) => (
        <div key={i} className='flex justify-between items-center py-3 border-b last:border-0'>
          <div className='space-y-2'>
            <div className='h-4 w-32 bg-gray-200 rounded' />
            <div className='h-3 w-24 bg-gray-100 rounded md:hidden' />
          </div>

          <div className='h-4 w-20 bg-gray-200 rounded' />
        </div>
      ))}
    </div>
  );
}

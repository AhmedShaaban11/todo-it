export default function Button({ type, onClick, className, form, children }) {
  return (
    <button type={type} onClick={onClick} form={form} className={`relative h-10 px-4 rounded-lg bg-blue-400 hover:bg-blue-300 ${className}`}>
      {children}
    </button>
  );
}
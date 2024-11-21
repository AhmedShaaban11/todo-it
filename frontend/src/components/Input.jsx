export default function Input({ type, id, name, className="", value=undefined, defaultValue=undefined, onChange, placeholder }) {
  return defaultValue ? (
    <input type={type} id={id} name={name} className={`w-full h-8 px-2 py-1 bg-slate-100  border-l-4 border-blue-400 ${className}`} placeholder={placeholder} defaultValue={defaultValue} />
  ) : (
    <input type={type} id={id} name={name} className={`w-full h-8 px-2 py-1 bg-slate-100  border-l-4 border-blue-400 ${className}`} placeholder={placeholder} value={value} onChange={onChange} />
  );
}
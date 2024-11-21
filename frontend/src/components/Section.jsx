export default function Section({ children, className }) {
  return (
    <section className={`relative w-1/3 h-full px-3 overflow-y-auto ${className}`}>
      {children}
    </section>
  );
}
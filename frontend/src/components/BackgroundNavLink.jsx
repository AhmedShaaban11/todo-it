import { NavLink } from "react-router-dom";

export default function BackgroundNavLink({ to, activeColor="bg-slate-200", pendingColor="bg-slate-300", inActiveColor="", className="" }) {
  return (
    <NavLink to={to} className={({ isActive, isPending }) => {
      const basicStyle = "w-full h-full absolute top-0 left-0 rounded-lg cursor-default";
      const hoverStyle = `${!isActive ? "hover:" + pendingColor : ""}`;
      const statusStyle = isActive ? activeColor : isPending ? pendingColor : inActiveColor;
      return `${basicStyle} ${hoverStyle} ${statusStyle} ${className}`;
    }} />
  );
}

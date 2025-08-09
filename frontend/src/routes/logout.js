import { redirect } from "react-router-dom";
import { logout } from "../fetches";

export async function action() {
  await logout();
  return redirect("/login");
}

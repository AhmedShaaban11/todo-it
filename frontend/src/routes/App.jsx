import {useEffect} from "react";
import {Outlet, useNavigate, useLoaderData} from "react-router-dom";
import "../index.css";
import Cookies from "js-cookie";
import {fetchCsrfToken, isLogged} from "../fetches.js";

export async function loader() {
  if (Cookies.get("XSRF-TOKEN") === undefined) { await fetchCsrfToken(); }
  const logged = await isLogged();
  return { logged };
}

export default function App() {
  const { logged } = useLoaderData();
  const navigate = useNavigate();

  useEffect(() => {
    navigate(logged ? "/space" : "/login");
  }, [logged]);

  return <Outlet />;
}

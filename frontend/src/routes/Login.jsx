import {redirect, Link, useFetcher} from "react-router-dom";
import {isLogged, login} from "../fetches.js";
import Button from "../components/Button";
import Input from "../components/Input";

export async function loader() {
  return null;
}

export async function action({ request }) {
  const formData = await request.formData();
  await login(formData.get("email"), formData.get("password"));
  return await isLogged() ? redirect("/space") : null;
}

export default function Login() {
  const fetcher = useFetcher();

  return (
    <>
      <div className="w-80 p-4 mx-auto mt-20 rounded-lg border-2 border-black">
        <h1 className="text-xl text-center pb-1 border-b-2 border-slate-200">Login</h1>
        <fetcher.Form method="POST" action="." className="py-2 flex flex-col gap-1">
          <label htmlFor="email">Email</label>
          <Input type="email" id="email" name="email" placeholder="Email" />
          <label htmlFor="password">Password</label>
          <Input type="password" id="password" name="password" placeholder="Password" />
          <div className="py-2 border-t-2 border-slate-200 mt-2 flex items-center gap-2">
            <Button type="submit">Login</Button>
            <Link to="/register" className="text-blue-600 hover:underline">Register instead?</Link>
          </div>
        </fetcher.Form>
      </div>
    </>
  )
}
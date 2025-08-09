import { Link, useFetcher } from "react-router-dom";
import Button from "../components/Button";
import Input from "../components/Input";
import { register } from "../fetches";

export async function action({ request }) {
  const data = await request.formData();
  await register(data.get("name"), data.get("email"), data.get("password"));
  return null;
}

export default function Register() {
  const fetcher = useFetcher();

  return (
    <div className="w-80 p-4 mx-auto mt-20 rounded-lg border-2 border-black">
      <h1 className="text-xl text-center pb-1 border-b-2 border-slate-200">Register</h1>
      <fetcher.Form method="POST" action="." className="py-2 flex flex-col gap-1">
        <label htmlFor="name">Name</label>
        <Input type="text" id="name" name="name" placeholder="Name" />
        <label htmlFor="email">Email</label>
        <Input type="email" id="email" name="email" placeholder="Email" />
        <label htmlFor="password">Password</label>
        <Input type="password" id="password" name="password" placeholder="Password" />
        <div className="py-2 border-t-2 border-slate-200 mt-2 flex items-center gap-2">
          <Button type="submit">Register</Button>
          <Link to="/login" className="text-blue-600 hover:underline">Login instead?</Link>
        </div>
      </fetcher.Form>
    </div>
  );
}
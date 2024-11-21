import { useEffect } from "react";
import {Outlet, useFetcher, useLoaderData} from "react-router-dom";
import "../index.css";
import {createTask, isLogged, readAllTasks, readList} from "../fetches.js";
import AddForm from "../components/AddForm.jsx";
import BackgroundNavLink from "../components/BackgroundNavLink.jsx";
import CompleteForm from "../components/CompleteForm.jsx";
import Section from "../components/Section.jsx";

export async function loader({ params }) {
  const logged = await isLogged();
  let tasks = [];
  if (logged) {
    tasks = await readAllTasks(params["listId"]);
  }
  return { tasks };
}

export async function action({ params, request }) {
  const data = await request.formData();
  if (data.get("title")) {
    await createTask(data.get("title"), params["listId"]);
  }
  return null;
}

function Task({ task }) {
  const fetcher = useFetcher();

  return (
    <li className="group relative h-12 px-2 flex justify-between items-center rounded-lg hover:bg-slate-300">
      <BackgroundNavLink to={`./task/${task["id"]}`} />
      <CompleteForm task={task} action={`./task/${task["id"]}/check`} />
      <fetcher.Form method="DELETE" action={`./task/${task["id"]}/delete`} className="hidden group-hover:block group-focus-within:block relative h-full">
        <button type="submit" className="text-2xl text-red-600 font-bold h-full px-1">🗑</button>
      </fetcher.Form>
    </li>
  );
}

export default function ListSection() {
  const { tasks } = useLoaderData();

  useEffect(() => console.log(tasks), [tasks]);

  return (
    <>
      <Section>
        <div className="h-12 text-lg flex items-center">
          <h2>Tasks</h2>
        </div>
        <AddForm title="Add Task" action="." />
        <ul className="py-2">
          {tasks.filter(task => !task["isCompleted"]).map(task => <Task key={task["id"]} task={task} />)}
        </ul>
        <details>
          <summary className="text-blue-600">Completed Tasks</summary>
          <ul className="py-2">
            {tasks.filter(task => task["isCompleted"]).map(task => <Task key={task["id"]} task={task} />)}
          </ul>
        </details>
      </Section>
      <Outlet />
    </>
  )
}
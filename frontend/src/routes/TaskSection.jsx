import { useState } from "react";
import { Form, useLoaderData, useFetcher } from "react-router-dom"
import {isLogged, readTask, updateTask} from "../fetches.js";
import Button from "../components/Button.jsx";
import Input from "../components/Input.jsx";
import "../index.css";
import CompleteForm from "../components/CompleteForm.jsx";
import Section from "../components/Section.jsx";

export async function loader({ params }) {
  const logged = await isLogged();
  let task = {};
  if (logged) {
    task = await readTask(params["taskId"]);
  }
  return { task }
}

export async function action({ params, request }) {
  const data = await request.formData();
  if (data.get("title")) {
    await updateTask(params["taskId"], data.get("title"), data.get("description"), data.get("isCompleted"));
  }
  return null
}

export default function TaskSection() {
  const { task } = useLoaderData();
  const fetcher = useFetcher();

  return (
    <Section>
      <div className="text-lg h-12 flex items-center">
        <h2>Edit Task</h2>
      </div>
      <CompleteForm labelText="Completion Status" task={task} isCompleted={task["isCompleted"] ? true : false} action="./check" className="py-3 border-y-2 border-slate-200" />
      <Form method="PUT" className="flex flex-col gap-2">
        <input type="hidden" name="isCompleted" value={task["isCompleted"]} />
        <div className="pt-2 flex flex-col gap-2">
          <label htmlFor="title">Title</label>
          <Input type="text" key={`text-${task["id"]}`} id="title" name="title" placeholder="Title" defaultValue={task["title"]} />
        </div>
        <div className="mt-2 flex flex-col gap-2">
          <label htmlFor="description">Description</label>
          <textarea key={`description-${task["id"]}`} name="description" id="description" placeholder="Description" defaultValue={`${task["description"] || ""}`} className="h-32 px-2 border-l-4 border-blue-400 bg-slate-100"></textarea>
        </div>
        <div className="flex gap-2 mt-2 pt-4 border-t-2 border-slate-200">
          <Button type="submit">Save</Button>
          <Button type="submit" form="delete-task-form" className="bg-red-400 hover:bg-red-300">Delete</Button>
        </div>
      </Form>
      <fetcher.Form id="delete-task-form" method="DELETE" action={`./delete`} className="hidden" />
    </Section>
  )
}
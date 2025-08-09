import { useFetcher } from "react-router-dom";

export default function CompleteForm({ labelText, task, action, className="" }) {
  const fetcher = useFetcher();

  return (
    <fetcher.Form method="PUT" action={action} className={`relative flex items-center gap-2 ${className}`}>
      <input type="checkbox" id={`is-completed-${task["id"]}`} name="isCompleted" value={!task["isCompleted"]} checked={task["isCompleted"]} onChange={e => fetcher.submit(e.currentTarget.form)} className="size-4 cursor-pointer" />
      <label htmlFor={`is-completed-${task["id"]}`} className="cursor-pointer">{labelText || task["title"]}</label>
      <input type="hidden" name="isCompleted" value={false} /> {/* This is a workaround for the form not submitting the checkbox value */}
    </fetcher.Form>
  );
}
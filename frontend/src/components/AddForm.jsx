import { useState } from "react";
import { Form } from "react-router-dom";
import Input from "./Input";

export default function AddForm({ title, action }) {
  const [val, setVal] = useState("");

  return (
    <Form method="POST" action={action} onSubmit={() => setVal("")} className="py-2 border-y-2 border-slate-200">
      <label htmlFor={`add-${title}`} className="sr-only">{title}</label>
      <Input type="text" id={`add-${title}`} name="title" placeholder={`+ ${title}`} value={val} onChange={e => setVal(e.target.value)} />
    </Form>
  );
}
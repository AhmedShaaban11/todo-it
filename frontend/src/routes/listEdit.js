import { redirect } from "react-router-dom";
import { updateList } from "../fetches";

export async function action({ params, request }) {
  const data = await request.formData();
  await updateList(params["listId"], data.get("title"));
  return redirect("/space");
}
import { redirect } from "react-router-dom";
import { deleteList } from "../fetches";

export async function action({ params }) {
  await deleteList(params["listId"]);
  return redirect("/space");
}

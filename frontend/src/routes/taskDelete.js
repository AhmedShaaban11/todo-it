import {deleteTask} from "../fetches.js";
import {redirect} from "react-router-dom";

export async function action({ params }) {
  await deleteTask(params["taskId"]);
  return redirect(`/space/list/${params["listId"]}`);
}
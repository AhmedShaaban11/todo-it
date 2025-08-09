import {checkTask} from "../fetches.js";

export async function action({ params, request }) {
  let formData = await request.formData();
  await checkTask(params["taskId"], formData.get("isCompleted"));
  return null;
}
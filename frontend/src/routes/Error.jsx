import {useRouteError} from "react-router-dom";

export default function Error() {
  const error = useRouteError();

  return (
    <section className="h-screen px-2 flex flex-col justify-center items-center gap-4 bg-red-200">
      <h1 className="text-6xl">{error.status || 500}</h1>
      <h2 className="text-3xl">{error.statusText || error.body || "Internal Server Error"}</h2>
    </section>
  )
}
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import App, { loader as appLoader } from './routes/App.jsx'
import ListsSection, { loader as listSectionLoader, action as listSectionAction } from './routes/ListsSection.jsx'
import ListSection, { loader as listLoader, action as listAction } from './routes/ListSection'
import TaskSection, { loader as taskLoader, action as taskAction } from './routes/TaskSection'
import Register, { action as registerAction } from './routes/Register.jsx'
import { action as logoutAction } from './routes/logout.js'
import { action as editListAction } from './routes/listEdit.js'
import { action as checkAction } from './routes/taskCheck.js'
import { action as deleteTaskAction } from './routes/taskDelete.js'
import { action as deleteListAction } from './routes/listDelete.js'
import Index from './routes/Index.jsx'
import Login, { action as loginAction } from "./routes/Login.jsx";
import Error from "./routes/Error.jsx";

const router = createBrowserRouter(createRoutesFromElements(
  <Route path="/" element={<App />} loader={appLoader} errorElement={<Error />}>
    <Route path="/register" element={<Register />} action={registerAction} />
    <Route path="/login" element={<Login />} action={loginAction} />
    <Route path="/logout" action={logoutAction} />
    <Route path="/space" element={<ListsSection />} loader={listSectionLoader} action={listSectionAction} shouldRevalidate={({ nextUrl }) => nextUrl.pathname === "/space" }>
      <Route index element={<Index width="w-2/3" msg="No List Selected" />} />
      <Route path="/space/list/:listId" element={<ListSection />} loader={listLoader} action={listAction}>
        <Route index element={<Index width="w-1/3" msg="No Task Selected" />} />
        <Route path="/space/list/:listId/edit" action={editListAction}/>
        <Route path="/space/list/:listId/delete" action={deleteListAction}/>
        <Route path="/space/list/:listId/task/:taskId" element={<TaskSection />} loader={taskLoader} action={taskAction}>
          <Route path="/space/list/:listId/task/:taskId/check" action={checkAction} />
          <Route path="/space/list/:listId/task/:taskId/delete" action={deleteTaskAction} />
        </Route>
      </Route>
    </Route>
  </Route>
))

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)

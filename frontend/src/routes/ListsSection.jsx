import { useState } from "react";
import { Outlet, useLoaderData, useFetcher, useNavigate } from "react-router-dom";
import "../index.css";
import {createList, readAllLists, isLogged, readUser} from "../fetches.js";
import AddForm from "../components/AddForm.jsx";
import BackgroundNavLink from "../components/BackgroundNavLink.jsx";
import Input from "../components/Input.jsx";
import Button from "../components/Button.jsx";
import Section from "../components/Section.jsx";

export async function loader() {
  const logged = await isLogged();
  let user = {};
  let lists = [];
  if (logged) {
    user = await readUser();
    lists = await readAllLists();
  }
  return { user, lists }
}

export async function action({ request }) {
  const data = await request.formData();
  if (data.get("title")) {
    await createList(data.get("title"));
  }
  return null
}

function ListEditModal({ isVisible, list, setIsEditListModalVisible }) {
  const fetcher = useFetcher();

  function handleClick() {
    setIsEditListModalVisible(false);
  }

  return (
    <>
      <div onClick={handleClick} className={`${isVisible ? "block" : "hidden"} absolute inset-0 size-full bg-slate-200/75 z-10`} />
      <div className={`${isVisible ? "block" : "hidden"} w-1/3 p-5 absolute top-1/4 left-1/3 rounded-lg bg-white border-2 border-black z-20 box-content`}>
        <h3 className="text-lg mb-4 border-b-2 border-slate-400">Edit List</h3>
        <fetcher.Form method="PUT" action={`./list/${list["id"]}/edit`} className="flex flex-col">
          <label htmlFor="title" className="mb-1">Title</label>
          <Input type="text" id="title" name="title" placeholder="Title" defaultValue={list["title"]} autoFocus />
          <div className="flex gap-2 mt-3 py-2 border-t-2 border-slate-400">
            <Button type="submit" onClick={handleClick}>Save</Button>
            <Button type="submit" form="delete-list-form" onClick={handleClick} className="bg-red-400 hover:bg-red-300">Delete</Button>
          </div>
        </fetcher.Form>
        <fetcher.Form id="delete-list-form" method="DELETE" action={`./list/${list["id"]}/delete`} className="hidden" />
      </div>
    </>
  );
}

function List({ list, onEdit }) {
  return (
    <li className="group relative h-12 px-2 flex justify-between items-center rounded-lg hover:bg-slate-300" onDragStart={() => console.log("DRAG_START")} onDragEnd={() => console.log("DRAG_END")}>
      <BackgroundNavLink to={`./list/${list["id"]}`} />
      <span className="relative">{list["title"]}</span>
      <div className="hidden group-hover:block group-focus-within:block h-full">
        <button type="button" className="relative text-xl pl-2 h-full" onClick={onEdit}>✎</button>
      </div>
    </li>
  )
}

function Navbar() {
  const { user } = useLoaderData();
  const fetcher = useFetcher();

  return (
    <nav className="w-full h-12 px-12 bg-blue-400 border-b-2 border-black flex justify-between items-center">
      <img src="src/assets/logo.png" alt="Todo-IT" className="h-full" />
      <div className="text-center">
        <p className="text-lg font-bold tracking-wider">{user["name"]}</p>
      </div>
      <fetcher.Form method="POST" action="/logout">
        <Button type="submit" className="bg-sky-200 hover:bg-blue-100">Logout</Button>
      </fetcher.Form>
    </nav>
  );
}

export default function ListsSection() {
  const { lists } = useLoaderData();
  const [isEditListModalVisible, setIsEditListModalVisible] = useState(false);
  const [listEditModalList, setListEditModalList] = useState({});

  return (
    <>
      <Navbar />
      <ListEditModal isVisible={isEditListModalVisible} list={listEditModalList} setIsEditListModalVisible={setIsEditListModalVisible} />
      <div className="w-full h-screen flex justify-between relative divide-x-2 divide-black">
        <Section>
          <div className="h-12 text-lg flex items-center">
            <h2>Lists</h2>
          </div>
          <AddForm title="Add List" action="." />
          <ul className="py-2">
            {lists.map(list => <List key={list["id"]} list={list} onEdit={() => { setIsEditListModalVisible(!isEditListModalVisible); setListEditModalList(list); }} />)}
          </ul>
        </Section>
        <Outlet />
      </div>
    </>
  )
}
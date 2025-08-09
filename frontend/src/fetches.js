import Cookies from "js-cookie"

const HOST = `http://localhost:${import.meta.env.VITE_BACKEND_PORT}/api/v1`;

export async function fetchCsrfToken() {
  await fetch(`${HOST}/access/csrf`, {
    method: "GET",
    credentials: "include"
  });
}

export async function register(name, email, password) {
  await fetch(`${HOST}/access/register`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ name, email, password })
  });
}

export async function login(email, password) {
  await fetch(`${HOST}/access/login`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ email, password })
  })
}

export async function isLogged() {
  return await fetch(`${HOST}/access/isLogged`, {
    method: "GET",
    credentials: "include"
  }).then(async res => await res.json());
}

export async function logout() {
  await fetch(`${HOST}/access/logout`, {
    method: "GET",
    credentials: "include"
  });
}

export async function readUser() {
  return await fetch(`${HOST}/access/profile`, {
    method: "GET",
    credentials: "include"
  }).then(async res => await res.json());
}

export async function readAllLists() {
  return await fetch(`${HOST}/list/readAll`, {
    method: "GET",
    credentials: "include",
    headers: {"X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")}
  }).then(async response => await response.json())
}

export async function createList(title) {
  await fetch(`${HOST}/list/create`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ title })
  })
}

export async function readList(id) {
  return await fetch(`${HOST}/list/read?id=${id}`, {
    method: "GET",
    credentials: "include",
  })
  .then(async res => {
    if (!res.ok) { throw new Response(res.body, res); }
    return await res.json();
  })
  .catch(res => { throw new Response(res.body, res) });
}

export async function updateList(id, title) {
  await fetch(`${HOST}/list/update`, {
    method: "PUT",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ id, title })
  });
}

export async function deleteList(id) {
  await fetch(`${HOST}/list/delete?id=${id}`, {
    method: "DELETE",
    credentials: "include",
    headers: {"X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")}
  });
}

export async function readTask(id) {
  return await fetch(`${HOST}/task/read?id=${id}`, {
    method: "GET",
    credentials: "include"
  }).then(async res => await res.json());
}

export async function updateTask(id, title, description, isCompleted) {
  await fetch(`${HOST}/task/update`, {
    method: "PUT",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ id, title, description, isCompleted })
  })
}

export async function readAllTasks(listId) {
  return await fetch(`${HOST}/task/readAll?listId=${listId}`, {
    method: "GET",
    credentials: "include"
  }).then(async res => await res.json());
}

export async function checkTask(id, isCompleted) {
  await fetch(`${HOST}/task/complete`, {
    method: "PUT",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ id, isCompleted })
  })
}

export async function createTask(title, listId) {
  await fetch(`${HOST}/task/create`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
    },
    body: JSON.stringify({ title, listId })
  });
}

export async function deleteTask(id) {
  await fetch(`${HOST}/task/delete?id=${id}`, {
    method: "DELETE",
    credentials: "include",
    headers: {"X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")}
  });
}
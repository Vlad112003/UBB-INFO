import Router from "koa-router";
import dataStore from "nedb-promise";
import { broadcast } from "./wss.js";

export class ItemStore {
  constructor({ filename, autoload }) {
    this.store = dataStore({ filename, autoload });
  }

  async find(props) {
    return this.store.find(props);
  }

  async findOne(props) {
    return this.store.findOne(props);
  }

  async insert(movie) {
    if (!movie.title || !movie.producer) {
      throw new Error("Missing title or producer!");
    }
    if (!movie.duration) {
      throw new Error("Duration is not a number!");
    }
    return this.store.insert(movie);
  }

  async update(props, item) {
    return this.store.update(props, item);
  }
}

const itemStore = new ItemStore({
  filename: "./db/items.json",
  autoload: true,
});

export const itemRouter = new Router();

itemRouter.get("/", async (ctx) => {
  const userId = ctx.state.user._id;
  ctx.response.body = await itemStore.find({ userId });
  ctx.response.status = 200; // ok
});

itemRouter.get("/:id", async (ctx) => {
  const userId = ctx.state.user._id;
  const item = await noteStore.findOne({ _id: ctx.params.id });
  const response = ctx.response;
  if (item) {
    if (item.userId === userId) {
      ctx.response.body = item;
      ctx.response.status = 200; // ok
    } else {
      ctx.response.status = 403; // forbidden
    }
  } else {
    ctx.response.status = 404; // not found
  }
});

const createItem = async (ctx, movie, response) => {
  try {
    const userId = ctx.state.user._id;
    console.log(userId);
    movie.userId = userId;
    movie.isNotSaved = false;

    //ca din android cand se creeaza un obiect nou, id-ul este "" si il sterge automat la urm rulare
    if (movie.id == "") {
      movie.id = undefined;
    }

    response.body = await itemStore.insert(movie);
    console.log(response.body);
    response.status = 201; // created
    broadcast(userId, {
      event: "created",
      payload: { updatedMovie: response.body },
    });
  } catch (err) {
    console.log(err);
    response.body = { message: err.message };
    response.status = 400; // bad request
  }
};

itemRouter.post(
  "/",
  async (ctx) => await createItem(ctx, ctx.request.body, ctx.response)
);

itemRouter.put("/:id", async (ctx) => {
  const movie = ctx.request.body;
  const id = ctx.params.id;
  const movieId = movie._id;
  const response = ctx.response;
  if (movieId && movieId !== id) {
    response.body = { message: "Param id and body _id should be the same" };
    response.status = 400; // bad request
    return;
  }
  if (!movieId) {
    await createItem(ctx, movie, response);
  } else {
    const userId = ctx.state.user._id;
    movie.userId = userId;
    const updatedCount = await itemStore.update({ _id: id }, movie);
    if (updatedCount === 1) {
      response.body = movie;
      response.status = 200; // ok
      movie.isNotSaved = false;
      broadcast(userId, {
        event: "updated",
        payload: { successMessage: "Updated movie!", updatedMovie: movie },
      });
    } else {
      response.body = { message: "Resource no longer exists" };
      response.status = 405; // method not allowed
    }
  }
});

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

  async insert(cpu) {
    if (!cpu.model || !cpu.manufacturer) {
      throw new Error("Missing model or manufacturer!");
    }
    if (!cpu.speed) {
      throw new Error("Speed is not a number!");
    }
    return this.store.insert(cpu);
  }

  async update(props, item) {
    return this.store.update(props, item);
  }

  async remove(props) {
    return this.store.remove(props);
  }
}

const itemStore = new ItemStore({
  filename: "./db/items.json",
  autoload: true,
});

export const itemRouter = new Router();

itemRouter.get("/", async (ctx) => {
  const userId = ctx.state.user._id;
  console.log("here");
  ctx.response.body = await itemStore.find({ userId });
  ctx.response.status = 200; // ok
});

itemRouter.get("/:id", async (ctx) => {
  const userId = ctx.state.user._id;
  const item = await itemStore.findOne({ _id: ctx.params.id });
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

const createItem = async (ctx, cpu, response) => {
  try {
    const userId = ctx.state.user._id;
    console.log(userId);
    cpu.userId = userId;
    if (cpu._id == "") cpu._id = undefined; // to save in db
    response.body = await itemStore.insert(cpu);
    console.log(response.body);
    response.status = 201; // created
    broadcast(userId, {
      event: "created",
      payload: { updatedCpu: response.body },
    });
  } catch (err) {
    console.log("here");
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
  const cpu = ctx.request.body;
  const id = ctx.params.id;
  const cpuId = cpu._id;
  const response = ctx.response;
  if (cpuId && cpuId !== id) {
    response.body = { message: "Param id and body _id should be the same" };
    response.status = 400; // bad request
    return;
  }
  if (!cpuId) {
    await createItem(ctx, cpu, response);
  } else {
    const userId = ctx.state.user._id;
    cpu.userId = userId;
    const updatedCount = await itemStore.update({ _id: id }, cpu);
    if (updatedCount === 1) {
      response.body = cpu;
      response.status = 200; // ok
      broadcast(userId, {
        event: "updated",
        payload: { successMessage: "Updated CPU!", updatedCpu: cpu },
      });
    } else {
      response.body = { message: "Resource no longer exists" };
      response.status = 405; // method not allowed
    }
  }
});

itemRouter.del("/:id", async (ctx) => {
  const userId = ctx.state.user._id;
  const item = await itemStore.findOne({ _id: ctx.params.id });
  if (item && userId !== item.userId) {
    ctx.response.status = 403; // forbidden
  } else {
    await itemStore.remove({ _id: ctx.params.id });
    ctx.response.status = 204; // no content
    broadcast(userId, { event: "deleted", payload: { updatedCpu: item } });
  }
});
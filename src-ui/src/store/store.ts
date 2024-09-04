import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";
import { api } from "./api/generatedApi";
import { queryErrorLogger } from "./middlewares/queryErrorLogger";
import TrackDayDateSlice, { trackDayDateSliceName } from "./TrackDayDate.Slice";

const reducer = combineReducers({
    [api.reducerPath]: api.reducer,
    [trackDayDateSliceName]: TrackDayDateSlice,
});

export const store = configureStore({
    reducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat([api.middleware, queryErrorLogger]),
});

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

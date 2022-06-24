import {configureStore} from "@reduxjs/toolkit";
import guestRefrigeratorSlice from "../modules/guestRefrigerator";
import userRefrigerator from "../modules/userRefrigerator";
import loginReducer from "../modules/login";
import myRecipesSlice from "../modules/myRecipes";

export const store = configureStore({
    reducer: {
        guestIngredients: guestRefrigeratorSlice,
        userIngredients: userRefrigerator,
        login: loginReducer,
        myRecipes: myRecipesSlice,
    },
    devTools: true
});

export type AppState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch

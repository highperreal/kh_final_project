import {createSlice} from "@reduxjs/toolkit";
import {AppState} from "../store";
import {InitialState} from "./userRefrigerator";

const initialState: InitialState = {
    ingredients: [
        {
            ingredientId: 0,
            ingredientName: "",
        }
    ]
}

export const guestRefrigeratorSlice = createSlice({
    name: 'guestIngredient',
    initialState,
    reducers: {
        updateGuestIngredient: (state, action) => {
            state.ingredients = [...state.ingredients, action.payload];
        },
        removeGuestIngredient: (state, action) => {
            const ingredients = state.ingredients.filter((ingredient) => (
                ingredient.ingredientId !== action.payload.ingredientId
            ))
            state.ingredients = ingredients;
        },
        deleteGuestIngredient: (state) => {
            state.ingredients = initialState.ingredients;
        }
    }
})

export default guestRefrigeratorSlice.reducer;
export const { updateGuestIngredient, removeGuestIngredient, deleteGuestIngredient } = guestRefrigeratorSlice.actions;
export const selectGuestIngredient = (state: AppState) => state.guestIngredients.ingredients;

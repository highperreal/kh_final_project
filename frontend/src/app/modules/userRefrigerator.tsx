import {createSlice} from "@reduxjs/toolkit";
import {AppState} from "../store";

export interface ChosenIngredients {
    fridgeId?: number,
    ingredientId: number,
    ingredientImage?: string,
    ingredientName: string,
    ingredientTypeName?: string,
    userid?: number,
    username?: string
}

export interface InitialState {
    ingredients: ChosenIngredients[]
}

const initialState: InitialState = {
    ingredients: [
        {
            fridgeId: 0,
            ingredientId: 0,
            ingredientImage: "",
            ingredientName: "",
            ingredientTypeName: "",
            userid: 0,
            username: ""
        }
    ]
}

export const userRefrigeratorSlice = createSlice({
    name: 'userIngredient',
    initialState,
    reducers: {
        setUserIngredient: (state, action) => {
            state.ingredients = action.payload.data
        },
        updateUserIngredient: (state, action) => {
            state.ingredients = [...state.ingredients, action.payload];
        },
        removeUserIngredient: (state, action) => {
            const ingredients = state.ingredients.filter((ingredient) => (
                ingredient.ingredientId !== action.payload.ingredientId
            ))
            state.ingredients = ingredients;
        },
        deleteUserIngredient: (state) => {
            state.ingredients = initialState.ingredients;
        }
    }
})

export default userRefrigeratorSlice.reducer;
export const { setUserIngredient, updateUserIngredient, removeUserIngredient, deleteUserIngredient } = userRefrigeratorSlice.actions;
export const selectUserIngredient = (state: AppState) => state.userIngredients.ingredients;

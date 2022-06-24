import {createSlice} from "@reduxjs/toolkit";
import {AppState} from "../store";
import {Recipes} from "../../pages/all-recipes/all-recipes";

const initialState:Recipes = {
    count: 0,
    data : [{
        id: 0,
        image: "",
        name : "",
        subtitle: "",
        likeQuantity: 0,
        calorie: 0,
        flavorName: "",
        myRecipeId: 0,
    }]
}

export const myRecipesSlice = createSlice({
    name: 'userIngredient',
    initialState,
    reducers: {
        setMyRecipes: (state, action) => {
            state.count = action.payload.count;
            state.data = action.payload.data;
        },
        updateMyRecipes: (state, action) => {
            state.count = state.count + 1
            state.data = [...state.data, action.payload];
        },
        removeMyRecipes: (state, action) => {
            if (state.count > 0) {
                state.count = state.count - 1
            }

            const recipes = state.data.filter((recipe) => (
                recipe.id !== action.payload
            ))
            state.data = recipes;
        },
        deleteMyRecipes: (state) => {
            state.count = initialState.count
            state.data = initialState.data;
        }
    }
})

export default myRecipesSlice.reducer;
export const {setMyRecipes,  updateMyRecipes ,removeMyRecipes, deleteMyRecipes} = myRecipesSlice.actions;
export const selectMyRecipes = (state: AppState) => state.myRecipes;

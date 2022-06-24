import React, {useEffect, useState} from 'react';
import Header from "./components/header/header";
import "./App.css";
import {Route, Routes} from 'react-router-dom';
import MainPage from "./pages/main-page/main-page";
import RecipeDetail from "./pages/recipe-detail/recipe-detail";
import AllRecipes from "./pages/all-recipes/all-recipes";
import MyRecipe from "./pages/my-recipe/my-recipe";
import RecommendRecipe from "./pages/recommend-recipe/recommend-recipe";
import MyRefrigerator from "./pages/my-refrigerator/my-refrigerator";
import MyPage from "./pages/my-page/my-page";
import Login from "./pages/login/login";

function App() {

    return (
        <div>
            <Header />
            <Routes>
                <Route path="/" element={<MainPage/>}></Route>
                <Route path="/recipe-detail" element={<RecipeDetail/>}></Route>
                <Route path="/recipe-detail/:id" element={<RecipeDetail/>}></Route>
                <Route path="/all-recipes" element={<AllRecipes/>}></Route>
                <Route path="/my-recipe" element={<MyRecipe/>}></Route>
                <Route path="/recommend-recipe" element={<RecommendRecipe/>}></Route>
                <Route path="/recommend-recipe/:userId" element={<RecommendRecipe/>}></Route>
                <Route path="/refrigerator" element={<MyRefrigerator/>}></Route>
                <Route path="/my-page" element={<MyPage/>}></Route>
                <Route path="/login" element={<Login/>}></Route>
            </Routes>
        </div>
    );
}

export default App;

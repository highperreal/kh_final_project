import React, {useEffect, useState} from "react";
import {Container, Spinner} from "react-bootstrap";
import RecipeCard from "../../components/card/recipe-card";
import styles from "../all-recipes/all-recipe.module.css";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useDispatch, useSelector} from "react-redux";
import {selectMyRecipes, setMyRecipes} from "../../app/modules/myRecipes";

const MyRecipe = () => {
    const navigate = useNavigate();
    const recipes = useSelector(selectMyRecipes);
    const dispatch = useDispatch();
    const [spinner, setSpinner] = useState(true)

    const getMyRecipes = async () => {
        await axios.get(`http://localhost:8080/myrecipe/v1?id=${sessionStorage.getItem("userId")}`)
            .then((res) => {
                setSpinner(false);
                dispatch(setMyRecipes(res.data))
            })
            .catch((err) => {
                console.error(err);
            })
    }

    useEffect(() => {
        if (!sessionStorage.getItem("token")) {
            navigate("/");
        } else {
            getMyRecipes()
        }
    }, [])

    return (
        <Container className={styles.container}>
            <h2 className={styles.title}>
                나의 레시피.
            </h2>
            <h3 className={styles.subTitle}>
                자꾸 생각나는 맛.
            </h3>
            <hr/>
            {spinner ?
                <Spinner animation="border" />
                :
                <RecipeCard recipes={recipes} my_recipe={true}/>
            }
        </Container>
    );
};

export default MyRecipe;

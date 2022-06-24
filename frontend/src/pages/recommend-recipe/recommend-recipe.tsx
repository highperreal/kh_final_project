import {useSelector} from "react-redux";
import {selectGuestIngredient} from "../../app/modules/guestRefrigerator";
import React, {useEffect, useState} from "react";
import {Col, Container, Row, Spinner} from "react-bootstrap";
import RecipeCard from "../../components/card/recipe-card";
import styles from "./recommend-recipe.module.css";
import axios from "axios";
import {Recipes} from "../all-recipes/all-recipes";
import {useNavigate, useParams} from "react-router-dom";
import {selectUserIngredient} from "../../app/modules/userRefrigerator";


const RecommendRecipe = () => {
    const {userId} = useParams();
    const guestIngredients = useSelector(selectGuestIngredient).filter((ingredient) => ingredient.ingredientId !== 0);
    const userIngredients = useSelector(selectUserIngredient).filter((ingredient) => ingredient.ingredientId !== 0);

    const navigate = useNavigate();
    const [spinner, setSpinner] = useState(true)
    const [recipes, setRecipes] = useState<Recipes>({
        count: 0,
        data: [{
            id: 0,
            image: "",
            name: "",
            subtitle: "",
            likeQuantity: 0,
            calorie: 0,
            flavorId: 0,
            flavorName: "",
        }]
    });

    const getRecipes = async () => {

        if (userId) {
            await axios.get(`http://localhost:8080/fridge/find?id=${userId}`)
                .then((res) => {
                    setRecipes(res.data);
                    setSpinner(false);
                })
                .catch((err) => {
                    console.error(err);
                })

        } else {

            const ids: number[] = []
            guestIngredients.map((guestIngredient) => ids.push(guestIngredient.ingredientId));

            const searchParams = {
                ingredientIds: ids.join(",")
            }

            await axios.get("http://localhost:8080/api/recommendRecipes", {params: searchParams})
                .then((res) => {
                    setRecipes(res.data);
                    setSpinner(false);
                })
                .catch((err) => {
                    console.error(err);
                })
        }
    }

    useEffect(() => {
        if (guestIngredients.length === 0 && userIngredients.length === 0) {
            navigate("/");
        }
        getRecipes();
    }, []);

    return (
        <Container className={styles.container}>
            <h2 className={styles.title}>
                추천 레시피.
            </h2>
            <h3 className={styles.subTitle}>
                오늘 이 메뉴는 어때요?!
            </h3>
            <hr/>
            <div className={styles.selectedIngredient}>
                <p>고른 재료</p>
                <Row>
                    {userId ?
                        <>
                            {userIngredients.map((ingredient) => (
                                <Col
                                    xs={3}
                                    key={ingredient.ingredientId}
                                >
                                    {ingredient.ingredientName}
                                </Col>
                            ))}
                        </>
                        :
                        <>
                            {guestIngredients.map((ingredient) => (
                                <Col
                                    xs={3}
                                    key={ingredient.ingredientId}
                                >
                                    {ingredient.ingredientName}
                                </Col>
                            ))}
                        </>
                    }
                </Row>
            </div>
            {spinner ?
                <Spinner animation="border" />
                :
                <RecipeCard recipes={recipes}/>
            }
        </Container>
    );
};

export default RecommendRecipe;

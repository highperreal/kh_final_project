import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import axios from "axios";
import styles from "./recipe-detail.module.css";
import {Recipes} from "../all-recipes/all-recipes";
import {removeMyRecipes} from "../../app/modules/myRecipes";

const RecipeDetail = () => {

    const {id} = useParams();
    const navigate = useNavigate();
    const [likeCheck, setLikeCheck] = useState(false)
    const [ingredients, setIngredients] = useState([""])
    const [steps, setSteps] = useState([""])
    const [myRecipes, setMyRecipes] = useState<Recipes>({
        count: 0,
        data: [{
            id: 0,
            image: "",
            name: "",
            subtitle: "",
            likeQuantity: 0,
            calorie: 0,
            flavorName: "",
        }]
    });

    const [recipe, setRecipe] = useState({
        id:0,
        name: "",
        subtitle: "",
        likeQuantity: 0,
        calorie: 0,
        image: "",
        recipeIngredient: "",
        steps: "",
        flavorName: "",
    })

    const getMyRecipes = async () => {
        await axios.get(`http://localhost:8080/myrecipe/v1?id=${sessionStorage.getItem("userId")}`)
            .then((res) => {
                setMyRecipes(res.data)
            })
            .catch((err) => {
                console.error(err);
            })
    }

    const checkMyRecipe = () => {
        if (typeof id === "string") {
            const recipe = myRecipes.data.filter(recipe => recipe.id === parseInt(id))
            if (recipe.length !== 0) {
                setLikeCheck(true)
            } else {
                setLikeCheck(false)
            }
        }
    }

    const getRecipe = async () => {
        await axios.get(`http://localhost:8080/api/recipe/v1/${id}`)
            .then((res) => {
                setRecipe(res.data)
                setIngredients(res.data.recipeIngredient.split(","))
                setSteps(res.data.steps.split("\n"))
            }).catch((err) => {
                console.error(err);
            });
    }

    const removeMyRecipe = async (id: number) => {
        await axios.delete("http://localhost:8080/myrecipe/delete", {data: {
                userid: sessionStorage.getItem("userId"),
                recipeid: id
            }})
            .catch((err) => {
                console.error(err)
            })
    }

    const updateMyRecipe = async (id: number) => {
        await axios.post("http://localhost:8080/myrecipe/add", {
            userid: sessionStorage.getItem("userId"),
            recipeid: id
        })
            .catch((err) => {
                console.error(err)
            })
    }

    const likeClickHandle = (check: boolean) => {
        if (check === true) {
            removeMyRecipe(recipe.id);
            setLikeCheck(false)
        } else {
            updateMyRecipe(recipe.id);
            setLikeCheck(true)
        }
    }

    useEffect(() => {
        if(!sessionStorage.getItem("userId")) {
            navigate("/");
        }
        getRecipe();
        getMyRecipes();
    }, [])

    useEffect(() => {
        checkMyRecipe()
    }, [myRecipes.data])

    return (
        <Container>
            <div className={styles.container}>
                <img className={styles.recipeImg} src={recipe.image}/>
            </div>
            <div className={styles.recipeInfoBox}>
                <Row>
                    <Col xs={6}>
                        <h2>{recipe.name}</h2>
                        <h5>{recipe.subtitle}</h5>
                    </Col>
                    <Col xs={6} className={styles.likeBtnBox}>
                        {likeCheck ?
                            <img onClick={() => likeClickHandle(likeCheck)} src="/images/heart.png"/>
                            :
                            <img onClick={() => likeClickHandle(likeCheck)} src="/images/emptyHeart.png"/>}
                    </Col>
                </Row>
                <hr/>
                <div className={styles.ingredientBox}>
                    <h5>재료.</h5>
                    <Row>
                        {ingredients.map((ingredient,index) => (
                            <Col xs={4} sm={3} lg={2}
                                 key={`ingredient=${index}`}
                            >
                                {ingredient}
                            </Col>
                        ))}
                    </Row>
                </div>
                <div className={styles.ingredientBox}>
                    <h5>순서.</h5>
                    {steps.map((step,index) => (
                        <p key={`step=${index}`}>
                            {step}
                        </p>
                    ))}
                </div>
            </div>
        </Container>
    );
};

export default RecipeDetail;

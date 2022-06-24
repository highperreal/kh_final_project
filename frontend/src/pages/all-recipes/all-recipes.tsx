import {Button, ButtonGroup, Col, Container, Form, Row, Spinner} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import RecipeCard from "../../components/card/recipe-card";
import styles from "./all-recipe.module.css";
import axios from "axios";

export interface Recipes {
    count: number,
    data: {
        id: number,
        image: string,
        name: string,
        subtitle: string,
        likeQuantity: number,
        calorie: number,
        flavorId?: number,
        flavorName: string,
        myRecipeId?: number,
    }[]
}

const AllRecipes = () => {

    const [recipes, setRecipes] = useState<Recipes>({
        count: 0,
        data : [{
            id: 0,
            image: "",
            name : "",
            subtitle: "",
            likeQuantity: 0,
            calorie: 0,
            flavorId: 0,
            flavorName: "",
        }]
    });

    const [flavors, setFlavors] = useState({
        count: 0,
        data: [{
            id: 0,
            name: "",
        }]
    })

    const [spinner, setSpinner] = useState(true)
    const [searchRecipe, setSearchRecipe] = useState("");
    const [likeBtn, setLikeBtn] = useState(false);
    const [titleBtn, setTitleBtn] = useState(true);
    const [calorieBtn, setCalorieBtn] = useState(true);

    const changeHandle = (e:React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        setSearchRecipe(e.target.value)
        console.log(searchRecipe)
    }

    const getRecipes = async () => {
        await axios.get("http://localhost:8080/api/recipe/v1")
            .then((res) => {
                setRecipes(res.data)
                setSpinner(false)
            })
            .catch((err) => {
                console.error(err);
            })
    }

    const getFlavors = async () => {
        await axios.get("http://localhost:8080/api/recipe/v1/flavor")
            .then((res) => {
                setFlavors(res.data)
            })
            .catch((err) => {
                console.error(err);
            })
    }

    const getFlavorRecipes = async (id: number) => {
        await axios.get(`http://localhost:8080/api/recipe/v1/list/${id}`)
            .then((res) => {
                setRecipes(res.data)
            }).catch((err) => {
                console.error(err);
            })
    }

    const searchRecipes = async () => {
        await axios.get(`http://localhost:8080/api/recipe/v2/list/name?name=${searchRecipe}`)
            .then((res) => {
                setRecipes(res.data)
            })
            .catch((err) => {
                console.error(err)
            })
    }

    const orderByLike = async () => {
        setLikeBtn(!likeBtn)
        await axios.get(`http://localhost:8080/api/recipe/v2/list/like?alignLike=${likeBtn}`)
            .then((res) => {
                setRecipes(res.data)
            })
            .catch((err) => {
                console.error(err)
            })
    }

    const orderByTitle = async () => {
        setTitleBtn(!titleBtn)
        await axios.get(`http://localhost:8080/api/recipe/v2/list/name?alignName=${titleBtn}`)
            .then((res) => {
                setRecipes(res.data)
            })
            .catch((err) => {
                console.error(err)
            })
    }

    const orderByCalorie = async () => {
        setCalorieBtn(!calorieBtn)
        await axios.get(`http://localhost:8080/api/recipe/v2/list/calorie?alignCalorie=${calorieBtn}`)
            .then((res) => {
                setRecipes(res.data)
            })
            .catch((err) => {
                console.error(err)
            })
    }

    useEffect(() => {
        getRecipes();
        getFlavors();
    },[])

    return (
        <Container className={styles.container}>
            <h2 className={styles.title}>
                전체 레시피.
            </h2>
            <h3 className={styles.subTitle}>
                뭘 좋아할지 몰라 다 준비했어요.
            </h3>
            <hr/>
            <div className={styles.flavorBox}>
                <p>
                    어떤 음식이 땡겨요?!
                </p>
                <Row>
                    {flavors.data.map((flavor) => (
                            <Col
                                key={`flavorId=${flavor.id}`}
                                as="p"
                                xs={6}
                                sm={4}
                            >
                                <Button
                                    onClick={() => {getFlavorRecipes(flavor.id)}}
                                    variant="outline-dark"
                                    size="sm"
                                >
                                    {flavor.name}
                                </Button>
                            </Col>
                    ))}
                </Row>
            </div>

            <div className={styles.searchBox}>
                <Form.Control
                    className={styles.searchBar}
                    type="text"
                    id="searchRecipe"
                    placeholder="먹고 싶은 메뉴를 골라보세요."
                    value={searchRecipe}
                    onChange={changeHandle}
                />
                <Button
                    className={styles.searchBtn}
                    variant="outline-success"
                    onClick={() => {searchRecipes()}}
                >
                    찾기
                </Button>
            </div>

            <div
                className={styles.orderBtn}
            >
                <ButtonGroup
                    aria-label="Basic example"
                >
                    <Button
                        onClick={() => {orderByTitle()}}
                        className={styles.searchBtn}
                        variant="outline-dark"
                    >
                        제목순
                    </Button>
                    <Button
                        onClick={() => {orderByLike()}}
                        className={styles.searchBtn}
                        variant="outline-dark"
                    >
                        좋아요순
                    </Button>
                    <Button
                        className={styles.searchBtn}
                        variant="outline-dark"
                        onClick={() => {orderByCalorie()}}
                    >
                        칼로리순
                    </Button>
                </ButtonGroup>
            </div>
            {spinner ?
                <Spinner animation="border" />
                :
                <RecipeCard recipes={recipes}/>
            }
        </Container>
    );
};

export default AllRecipes;

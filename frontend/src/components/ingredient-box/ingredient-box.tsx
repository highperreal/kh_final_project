import React, {useEffect, useState} from 'react';
import styles from "./ingredient-box.module.css";
import {Col, Row} from "react-bootstrap";
import Ingredient from "../ingredient/ingredient";
import axios from "axios";

interface IProps {
    chosenIngredients: {
        ingredientId: number,
        ingredientName: string,
    }[],
    user: boolean,
}

interface Ingredients {
    count: number,
    data: [
        {
            ingredientTypeId: number,
            ingredientTypeName: string,
            image: string,
            ingredientDtos: [
                {
                    ingredientId: number,
                    ingredientName: string
                },
            ]
        },
    ]
}

const IngredientBox = (props: IProps) => {

    const {chosenIngredients , user} = props
    const [ingredients , setIngredients] = useState<Ingredients>({
        count: 0,
        data: [
            {
                ingredientTypeId: 0,
                ingredientTypeName: "",
                image: "",
                ingredientDtos: [
                    {
                        ingredientId: 0,
                        ingredientName: ""
                    },
                ]
            },
        ]
    })

    const getIngredients = async () => {
        await axios.get("http://localhost:8080/fridge/v3")
            .then((res) => {
                setIngredients(res.data)
            }).catch((err) => {
                console.error(err);
            })
    }

    useEffect(() => {
        getIngredients()
    }, [])

    return (
        <div className={styles.ingredientBox}>
            {ingredients.data.map((ingredient) => (
                <div key={`ingredient-${ingredient.ingredientTypeId}`}>
                    <Row>
                        <Col
                            sm={3}
                            lg={2}
                        >
                            <img
                                src={ingredient.image}
                                alt={ingredient.ingredientTypeName}
                                className={styles.img}
                            />
                        </Col>
                        <Col
                            className={styles.ingredientType}
                            sm={9}
                            lg={10}
                        >
                            {ingredient.ingredientTypeName}
                        </Col>
                    </Row>
                    <Row className={styles.ingredientRow}>
                        {ingredient.ingredientDtos.map((ingredientDto) => (
                            <Ingredient
                                user={user}
                                key={`ingredientDetail-${ingredientDto.ingredientId}`}
                                chosenIngredients={chosenIngredients}
                                ingredientDto={ingredientDto}
                            />
                        ))}
                    </Row>
                    <hr/>
                </div>
            ))}
        </div>
    );
};

export default IngredientBox;
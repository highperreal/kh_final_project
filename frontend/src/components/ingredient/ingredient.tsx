import React, {useEffect, useState} from 'react';
import {Col} from "react-bootstrap";
import {removeGuestIngredient,updateGuestIngredient} from "../../app/modules/guestRefrigerator";
import {useDispatch} from "react-redux";
import {removeUserIngredient, updateUserIngredient} from "../../app/modules/userRefrigerator";
import axios from "axios";

interface IProps {
    chosenIngredients: {
        ingredientId: number,
        ingredientName: string,
    }[],

    ingredientDto: {
        ingredientId: number,
        ingredientName: string,
    },
    user: boolean,
}

const Ingredient = (props: IProps) => {

    const {chosenIngredients, ingredientDto, user} = props
    const dispatch = useDispatch();
    const duplicateIngredient = chosenIngredients.filter((ingredient) => ingredient.ingredientId === ingredientDto.ingredientId);


    const [styles, setStyles] = useState(
        {
            fontSize: 17,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            cursor: "pointer",
            height: 40,
            color: "inherit"
        }
    )

    const removeIngredient = async (id: number) => {
        await axios.delete("http://localhost:8080/fridge/delete", {
            data: {
                userid: sessionStorage.getItem("userId"),
                ingredientid: id,
            }
        })
            .catch((err) => {
                console.error(err)
            })
    }

    const updateIngredient = async (id: number) => {
        await axios.post("http://localhost:8080/fridge/create",
            {
                userid: sessionStorage.getItem("userId"),
                ingredientid: id,
            }
        )
            .catch((err) => {
                console.error(err)
            })
    }

    const updateIngredientClick = () => {

        if (duplicateIngredient.length === 0) {

            if (user === true) {
                dispatch(updateUserIngredient(ingredientDto))
                updateIngredient(ingredientDto.ingredientId)
            } else {
                dispatch(updateGuestIngredient(ingredientDto))
            }
            setStyles({...styles, color: "lightGray"})

        } else {

            if (user === true) {
                dispatch(removeUserIngredient(ingredientDto))
                removeIngredient(ingredientDto.ingredientId)
            } else {
                dispatch(removeGuestIngredient(ingredientDto))
            }
            setStyles({...styles, color: "inherit"})

        }
    }

    useEffect(() => {
        if (duplicateIngredient.length !== 0) {
            setStyles({...styles, color: "lightGray"})
        }
    }, [chosenIngredients])

    useEffect(() => {
        if (duplicateIngredient.length === 0) {
            setStyles({...styles, color: "inherit"})
        }
    }, [chosenIngredients])

    return (
        <Col
            as="p"
            style={styles}
            xs={3}
            onClick={() => {
                updateIngredientClick()
            }}
        >
            {ingredientDto.ingredientName}
        </Col>
    );
};

export default Ingredient;
import classes from "./BloodDonation.module.css";
import { useState } from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

const BloodDonation = () => {
    const { id } = useParams();
    const [formData, setFormData] = useState({
        id:id
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Here you can perform AJAX request with formData
        setFormData(prevData => ({
            ...prevData,
            id: id
        }));

        axios.post('http://localhost:8080/bloodregistry/registrations', formData, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem("token")
            }
        },{ withCredentials: true })
            .then(response => {
                // Handle successful response here
                if(response.status == 200) {
                    console.log(response);
                    alert("Επιτυχής Καταχώρησ!");

                }})
            .catch(error => {
                // Handle error here5
                if(error.response.status ==500 ){
                    alert("Εισάγατε λάθος στοιχεία!");
                    console.log('Error:', error.response);
                }

            });
    };

    return (
        <>
            <div className={classes.blood_data_page}>
                <div className={classes.blood_data}>
                    <form onSubmit={handleSubmit}>
                        <span className={classes.logo}>
                            <h2>Δημιουργία Αίτησης</h2>
                        </span>
                        <button type="submit">Καταχώρηση Αίτησης</button>
                    </form>
                </div>
            </div>
        </>
    );
};

export default BloodDonation;

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import classes from "../blood_donation_form/BloodDonation.module.css";
import axios from "axios";

const UserCreate = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    specialty: "",
    role: "",
    address: "",
    phone: "",
    password: "",
    email: "",
    username: "",
  });

  const navigate = useNavigate();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    axios
      .post("http://localhost:8080/api/users/register", formData, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        // Handle successful response here
        if (response.status === 201) {
          alert("Επιτυχής καταχώρηση!");
          goBack();
        }
      })
      .catch((error) => {
        alert("Κάτι πήγε λάθος!");
        console.log("Error:", error.response);
      });
  };

  const goBack = () => {
    navigate("/users");
  };

  return (
    <div className={classes.blood_data_page}>
      <div className={classes.blood_data}>
        <form onSubmit={handleSubmit}>
          <span className={classes.logo}>
            <h2>Δημιουργία Χρήστη</h2>
          </span>
          <input
            placeholder="Όνομα"
            name="firstName"
            type="text"
            value={formData.firstName} // Corrected value
            onChange={handleChange}
            required
          />
          <input
            placeholder="Έπώνυμο"
            name="lastName"
            type="text"
            value={formData.lastName} // Corrected value
            onChange={handleChange}
            required
          />
          <input
            placeholder="Username"
            name="username"
            type="text"
            value={formData.username} // Corrected value
            onChange={handleChange}
            required
          />
          <input
            placeholder="Email"
            name="email"
            type="email"
            value={formData.email} // Corrected value
            onChange={handleChange}
            required
          />
          <input
            placeholder="Διεύθυνση"
            name="address"
            type="text"
            value={formData.address} // Corrected value
            onChange={handleChange}
            required
          />
          <input
            placeholder="Κινητό Τηλέφωνο"
            name="phone"
            type="text"
            value={formData.phone}
            onChange={handleChange}
          />
          <input
            placeholder="Password"
            name="password"
            type="password"
            value={formData.password} // Corrected value
            onChange={handleChange}
            required
          />
          <div className={classes.sex}>
            <label>
              Πολίτης
              <input
                type="radio"
                name="role" // Corrected name
                value="0"
                checked={formData.role === "0"}
                onChange={handleChange}
              />
            </label>
            <label>
              Ιατρός
              <input
                type="radio"
                name="role" // Corrected name
                value="1"
                checked={formData.role === "1"}
                onChange={handleChange}
              />
            </label>
          </div>
          {formData.role === "1" && (
            <>
              <input
                placeholder="Ειδικότητα"
                name="specialty"
                type="text"
                value={formData.specialty} // Corrected value
                onChange={handleChange}
                required
              />
            </>
          )}
          <div className={classes.buttons}>
            <button className={classes.button_success} type="submit">
              Δημιουργία Χρήστη
            </button>
            <button onClick={goBack} className={classes.button_danger}>
              Ακύρωση
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UserCreate;

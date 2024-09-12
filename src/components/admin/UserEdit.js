import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import classes from "../blood_donation_form/BloodDonation.module.css";
import axios from "axios";

const UserEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    role: "", // Assuming user role
    address: "", // Assuming user region
    phone: "", // Assuming user phone
    specialty: "",
  });

  useEffect(() => {
    // Fetch user data on component mount
    axios
      .get(`http://localhost:8080/api/users/${id}`, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setFormData(response.data);
        }
      })
      .catch((error) => {
        alert("Κάτι πήγε λάθος!");
        console.log("Error:", error.response);
      });
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    axios
      .put(
        `http://localhost:8080/api/users/update/${id}`,
        formData,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        },
        { withCredentials: true }
      )
      .then((response) => {
        // Handle successful response here
        if (response.status === 200) {
          console.log(response);
          navigate("/users");
        }
      })
      .catch((error) => {
        alert("Κάτι πήγε λάθος!");
        console.log("Error:", error.response);
      });
    console.log(formData);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div className={classes.blood_data_page}>
      <div className={classes.blood_data}>
        <form onSubmit={handleSubmit}>
          <span className={classes.logo}>
            <h2>Επεξεργασία Χρήστη</h2>
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
          />
          <input
            placeholder="Username"
            name="username"
            type="text"
            value={formData.username}
            onChange={handleChange}
            required
          />
          <input
            placeholder="Email"
            name="email"
            type="email"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <input
            placeholder="Διεύθυνση"
            name="address"
            type="text"
            value={formData.address}
            onChange={handleChange}
            required
          />

          {formData.role === "DOCTOR" && (
            <input
              placeholder="Ειδικότητα"
              name="specialty"
              type="text"
              value={formData.specialty}
              onChange={handleChange}
              required
            />
          )}

          <div className={classes.buttons}>
            <button className={classes.button_success} type="submit">
              Επεξεργασία Χρήστη
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

export default UserEdit;

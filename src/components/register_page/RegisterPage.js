import classes from "./RegisterPage.module.css";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

const RegisterPage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    email: "",
    firstName: "",
    lastName: "",
    address: "",
    role: "0",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const registerFunc = (event) => {
    event.preventDefault();

    axios
      .post(
        "http://localhost:8080/api/users/register",
        formData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
        { withCredentials: true }
      )
      .then((response) => {
        // Handle successful response here
        if (response.status === 201) {
          alert("Η εγγραφή σας ολοκληρώθηκε επιτυχώς");
          navigate("/");
        }
      })
      .catch((error) => {
        // Handle error here5
        if (error.response.status === 401) {
          alert("Εισάγατε λάθος στοιχεία!");
        } else {
          alert("Κάποιο σφάλμα προέκυψε!");
        }
      });
  };

  return (
    <fragment>
      <div className={classes.register_page}>
        <div className={classes.register}>
          <form onSubmit={registerFunc}>
            <span className={classes.logo}>
              <h2>Εγγραφή</h2>
            </span>
            <input
              placeholder="username"
              name="username"
              type="text"
              onChange={handleChange}
            />
            <input
              placeholder="email"
              name="email"
              type="email"
              onChange={handleChange}
            />
            <input
              placeholder="όνομα"
              name="firstName"
              type="text"
              onChange={handleChange}
            />
            <input
              placeholder="επώνυμο"
              name="lastName"
              type="text"
              onChange={handleChange}
            />
            <input
              placeholder="διεύθυνση"
              name="address"
              type="text"
              onChange={handleChange}
            />
            <div className={classes.role}>
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
                  name="role"
                  disabled
                  value="1"
                  checked={formData.role === "1"}
                  onChange={handleChange}
                />
              </label>
            </div>
            <input
              placeholder="password"
              name="password"
              type="password"
              onChange={handleChange}
            />
            <button>Εγγραφή</button>
            <div className={classes.reg}>
              <Link style={{ color: "black" }} to="/">
                <span>Έχετε ήδη λογαριασμό;</span>
              </Link>
            </div>
          </form>
        </div>
      </div>
    </fragment>
  );
};

export default RegisterPage;

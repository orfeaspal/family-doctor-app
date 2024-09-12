import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import classes from "./UserCreate.module.css";
import AppPagination from "../Pagination/AppPagination";
import axios from "axios";

const items_per_page = 6;
const Users = () => {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();
  const [page, setPage] = useState(1);
  const handleChangePagination = (event, value) => {
    setPage(value);
  };

  useEffect(() => {
    axios
      .get(
        "http://localhost:8080/api/users",
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
        if (response.status == 200) {
          setUsers(response.data);
        }
      })
      .catch((error) => {
        console.log("Error:", error.response);
      });
  }, []);

  const Removefunction = async (id) => {
    if (window.confirm("Do you want to remove?")) {
      await axios
        .delete(`http://localhost:8080/api/users/delete/${id}`, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          alert("Επιτυχής Διαγραφή!");
          window.location.reload();
        })
        .catch((err) => {});
    }
  };

  return (
    <div className={classes.users}>
      <div className="container">
        <div className="card">
          <div className="card-title" style={{ textAlign: "center" }}>
            <h2>Χρήστες Συστήματος</h2>
          </div>
          <div
            className="card-body"
            style={{
              display: "flex",
              alignItems: "flex-start",
              justifyContent: "flex-start",
              gap: "0.4rem",
              flexDirection: "column",
            }}
          >
            <div className="divbtn">
              <Link to="/create_users" className="btn btn-success">
                Add New (+)
              </Link>
            </div>

            <table className="table table-bordered">
              <thead className="bg-dark text-white">
                <tr>
                  <td>ID</td>
                  <td>Email</td>
                  <td>Username</td>
                  <td>Ρόλος</td>
                  <td>Ενέργειες</td>
                </tr>
              </thead>
              <tbody>
                {users
                  .slice((page - 1) * items_per_page, page * items_per_page)
                  .map(
                    (user) =>
                      user.role.roleId !== 1 && (
                        <tr key={user.id}>
                          <td>{user.id}</td>
                          <td>{user.email}</td>
                          <td>{user.username}</td>
                          <td>{user.role}</td>
                          <td
                            style={{
                              display: "flex",
                              alignItems: "flex-start",
                              justifyContent: "flex-start",
                              gap: "0.25rem",
                            }}
                          >
                            <a
                              onClick={() => {
                                navigate("/edit_users/" + user.id);
                              }}
                              className="btn btn-warning"
                            >
                              Edit
                            </a>
                            <button
                              type="button"
                              onClick={() => Removefunction(user.id)}
                              className="btn btn-danger"
                            >
                              Remove
                            </button>{" "}
                          </td>
                        </tr>
                      )
                  )}
                <AppPagination
                  total_cards={users.length}
                  items_per_page={items_per_page}
                  page={page}
                  onChange={handleChangePagination}
                />
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Users;

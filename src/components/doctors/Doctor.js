import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Doctor.module.css";
import AppPagination from "../Pagination/AppPagination";
import axios from "axios";

const items_per_page = 6;
const Doctor = () => {
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();
  const [page, setPage] = useState(1);

  // Assuming you have a way to get the logged-in doctor's ID
  const doctorId = localStorage.getItem("id"); // Replace this with the actual doctor ID

  const handleChangePagination = (event, value) => {
    setPage(value);
  };

  // Function to fetch requests
  const fetchRequests = () => {
    axios
      .get("http://localhost:8080/api/requests/doctor/" + doctorId, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setRequests(response.data);
        }
      })
      .catch((error) => {
        console.log("Error fetching requests:", error.response);
      });
  };

  useEffect(() => {
    fetchRequests(); // Call fetchRequests when the component mounts
  }, []);

  const handleRequestClick = (requestId, newStatus) => {
    axios
      .patch(
        "http://localhost:8080/api/requests/updateRequest/" +
          requestId +
          "/" +
          newStatus
      )
      .then((response) => {
        if (response.status === 200) {
          alert("Η ενέργεια σας ολοκληρώθηκε επιτυχώς!");
          fetchRequests(); // Refetch requests after successful update
        }
      })
      .catch((error) => {
        console.error("Error updating request:", error); // Log for debugging
        alert("Κάποιο σφάλμα προέκυψε κατά την καταχώρηση της αίτησης σας!");
      });
  };

  return (
    <div className={classes.doctor}>
      <div className="container">
        <div className="card">
          <div className="card-title" style={{ textAlign: "center" }}>
            <h2>Οι αιτήσεις μου</h2>
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
            <table className="table table-bordered">
              <thead className="bg-dark text-white">
                <tr>
                  <td>ID Αίτησης</td>
                  <td>Ονοματεπώνυμο Πολίτη</td>
                  <td>E-mail Πολίτη</td>
                  <td>Ενέργειες</td>
                </tr>
              </thead>
              <tbody>
                {requests
                  .slice((page - 1) * items_per_page, page * items_per_page)
                  .map((request) => {
                    console.log(
                      "Request ID:",
                      request.id,
                      "Status:",
                      request.status
                    );
                    return (
                      <tr key={request.id}>
                        <td>{request.id}</td>
                        <td>{request.citizenFullName}</td>
                        <td>{request.citizenEmail}</td>
                        {request.status === "PENDING" ? (
                          <td
                            style={{
                              display: "flex",
                              alignItems: "flex-start",
                              justifyContent: "flex-start",
                              gap: "0.25rem",
                            }}
                          >
                            <button
                              onClick={() =>
                                handleRequestClick(request.id, "APPROVED")
                              }
                              className="btn btn-success"
                            >
                              Αποδοχή
                            </button>
                            <button
                              onClick={() =>
                                handleRequestClick(request.id, "REJECTED")
                              }
                              type="button"
                              className="btn btn-danger"
                            >
                              Απόρριψη
                            </button>
                          </td>
                        ) : (
                          <td>{request.status}</td>
                        )}
                      </tr>
                    );
                  })}
              </tbody>
            </table>
            <AppPagination
              total_cards={requests.length}
              items_per_page={items_per_page}
              page={page}
              onChange={handleChangePagination}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Doctor;

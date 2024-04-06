<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "b";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}


// check for required fields
if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {

    $name = $_POST['name'];
    $price = $_POST['price'];
    $description = $_POST['description'];

    // include db connect class
    //require_once __DIR__ . '/db_connect.php';

    // connecting to db
    //$db = new DB_CONNECT();

    $sql = "INSERT INTO products(name, price, description) VALUES('$name', '$price', '$description')";

if ($conn->query($sql) === TRUE) {
    $response["success"] = 1;
    $response["message"] = "Product successfully created.";
    // echoing JSON response
    echo json_encode($response);
    //echo "New record created successfully";
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
  //echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();


} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>


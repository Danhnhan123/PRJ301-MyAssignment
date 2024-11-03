<%-- 
    Document   : login.jsp
    Created on : Nov 3, 2024, 2:12:04 PM
    Author     : Ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login - Company</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            html, body {
                width: 100%;
                height: 100%;
                overflow: hidden;
            }
            body {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                background-image: url("view/image/anh1.jpg");
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }
            .container {
                display: flex;
                width: 700px;
                background-color: #fff;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                overflow: hidden;
            }
            .form-container {
                padding: 40px;
                width: 50%;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }
            .form-container h2 {
                color: #333;
                margin-bottom: 20px;
            }
            .form-container p {
                color: #666;
                margin-bottom: 30px;
                font-size: 14px;
            }
            .form-container input[type="text"],
            .form-container input[type="password"] {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
            }
            .form-container input[type="submit"] {
                width: 100%;
                padding: 12px;
                background-color: #ff6600;
                border: none;
                color: white;
                font-weight: bold;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
            }
            .form-container input[type="submit"]:hover {
                background-color: #e55a00;
            }
            .right-container {
                width: 50%;
                background-image: url("view/image/anh2.jpg"); /* Replace with your image path */
                background-size: cover;
                background-position: center;
                position: relative;
                color: white;
                text-align: center;
            }
            .right-container .overlay {
                background-color: rgba(0, 0, 0, 0.5);
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                padding: 20px;
            }
            .right-container h3 {
                font-size: 24px;
                margin-bottom: 10px;
            }
            .right-container p {
                font-size: 16px;
                margin-bottom: 0;
            }
            .error-message {
                color: red;
                margin-bottom: 20px;
                font-size: 14px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="form-container">
                <h2>Sign In</h2>
                <p>Công nhân, Nhân viên, Cán bộ Công ty</p>
                <form action="login" method="POST">
                    <table>
                        <tr>
                            <td>Username:</td>
                            <td><input type="text" name="username"/></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" name="password"/></td>
                        </tr>
                    </table>
                    <div class="error-message">${requestScope.error}</div>
                    <input type="submit" value="Sign in"/>
                </form>
            </div>
            <div class="right-container">
                <div class="overlay">
                    <h3>DN limited liability company</h3>
                </div>
            </div>
        </div>
    </body>
</html>

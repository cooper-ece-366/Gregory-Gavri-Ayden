
import GoogleLogin from 'react-google-login';
import axios from "axios";
const LoginButton = () => {

    const responseGoogle = async (response) => {
        const { tokenObj: { id_token } } = response;
        console.log(response);
        const data = await axios.post("http://localhost:4567/login", { id_token });
        console.log(data);
    }

    const styleSheet = {
        button: {
            background: "#781C10",
            color: "white",
            border: "solid #781C10",
            fontFamily: "'Sen', sans-serif",
            height: "40px",
            width: "120px",
            marginLeft: "20px",
            padding: "0px",
            fontSize: "0.5em",
            borderRadius: "5px",
            cursor: "pointer",
            alignSelf: "center",
        }
    }


    return (
        // <div>
        <GoogleLogin
            clientId={process.env.GOOGLE_CLIENT_ID}
            buttonText="Login"
            render={renderProps => (
                <button style={styleSheet.button} onClick={renderProps.onClick} disabled={renderProps.disabled}>Login</button>
            )}
            onSuccess={responseGoogle}
            onFailure={() => { console.error("Login Failed!") }}
            cookiePolicy={'single_host_origin'}
        />
    )
}

export default LoginButton;

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
            marginLeft: "20px",
        }
    }


    return (
        <div style={styleSheet.button}>
            <GoogleLogin
                clientId={process.env.GOOGLE_CLIENT_ID}
                buttonText="Login"
                onSuccess={responseGoogle}
                onFailure={() => { console.error("Login Failed!") }}
                cookiePolicy={'single_host_origin'}
            />
        </div>
    )
}

export default LoginButton;
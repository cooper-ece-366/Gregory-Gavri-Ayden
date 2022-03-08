
import GoogleLogin from 'react-google-login';
import axios from "axios"; 
const LoginButton = ()=>{

    const responseGoogle = async (response)=>{
        const {tokenObj: {id_token}} = response;
        console.log(response); 
        const {data} = await axios.post("http://localhost:4567/login", {id_token}); 
        console.log(data); 
    }


    return (
        <div>
            <GoogleLogin
            clientId={"484987685606-9i6j1tee1tjjs60ufho8iubh4l1getgu.apps.googleusercontent.com"}
            buttonText="Login"
            onSuccess={responseGoogle}
            onFailure={()=>{console.error("Login Failed!")}}
            cookiePolicy={'single_host_origin'}
            />
        </div>
    )
}

export default LoginButton;
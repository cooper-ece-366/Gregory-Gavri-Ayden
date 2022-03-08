
import GoogleLogin from 'react-google-login';
import axios from "axios"; 
import {useUserContext} from '../../../Contexts/UserContext';
const LoginButton = ()=>{

    const {login} = useUserContext(); 

    const responseGoogle = async (response)=>{
        login(response);
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
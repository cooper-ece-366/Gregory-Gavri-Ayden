
import env from "../../../env.js"; 
import GoogleLogin from 'react-google-login';
import {useUserContext} from '../../../Contexts/UserContext';
const LoginButton = ()=>{

    const {login} = useUserContext(); 

    const responseGoogle = async (response)=>{
        login(response);
    }


    return (
        <div>
            <GoogleLogin
            clientId={env.GOOGLE_CLIENT_ID}
            buttonText="Login"
            onSuccess={responseGoogle}
            onFailure={()=>{console.error("Login Failed!")}}
            cookiePolicy={'single_host_origin'}
            />
        </div>
    )
}

export default LoginButton;
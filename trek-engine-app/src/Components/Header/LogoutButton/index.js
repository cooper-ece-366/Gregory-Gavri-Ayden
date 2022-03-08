import { GoogleLogout } from "react-google-login";
import { useUserContext } from "../../../Contexts/UserContext";

const LogoutButton = () => {

    const { logout } = useUserContext();
    const onSuccess = (response) => {
        logout();
    }
    const onFailure = response=> {
        console.error("Logout Failed!");
        console.error(response); 
    }


    return (<div>
        <GoogleLogout
            clientId={process.env.GOOGLE_CLIENT_ID}
            buttonText="Logout"
            onLogoutSuccess={onSuccess}
            onFailure={onFailure}
        />
    </div>)

}

export default LogoutButton;
import env from "../../../env.js";
import { GoogleLogout } from "react-google-login";
import { useUserContext } from "../../../Contexts/UserContext";

const LogoutButton = () => {

    // Written By Gavri Kepets
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


    // Written By Gregory Presser
    const { logout } = useUserContext();
    const onSuccess = (response) => {
        logout();
    }
    const onFailure = response => {
        console.error("Logout Failed!");
        console.error(response);
    }


    return (<div>
        <GoogleLogout
            clientId={env.GOOGLE_CLIENT_ID}
            buttonText="Logout"
            render={renderProps => (
                <button style={styleSheet.button} onClick={renderProps.onClick} disabled={renderProps.disabled}>Logout</button>)}
            onLogoutSuccess={onSuccess}
            onFailure={onFailure}
        />
    </div>)

}

export default LogoutButton;
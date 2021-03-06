// Written By Gregory Presser and styled by Gavri Kepets
import LoginButton from "./LoginButton"
import Logo from "../Utils/Logo"
import LogoutButton from "./LogoutButton";
import { useUserContext } from "../../Contexts/UserContext";
import { cloneElement } from "react";
import { Link } from 'react-router-dom';

const styleSheet = {
    root: {
        position: "fixed",
        width: "100%",
        height: "70px",
        backgroundColor: "rgba(1,5,20,0.35)",
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        top: "0",
        left: "0",
        fontSize: "2em",
        zIndex: "1",
        padding: "10px",
        boxSizing: "border-box",
        userSelect: "none"
    },
    left: {
        display: "flex",
        justifyContent: "center",
        alignSelf: "center",
        alignContent: "center",
        textDecoration: "none"
    },
    right: {
        display: "flex",
        justifyContent: "center",
        alignSelf: "center",
        alignContent: "center"
    },
    name: {
        lineHeight: "50px",
        marginLeft: "10px",
        color: "white",
        textDecoration: "none"
    },
    Link: {
        color: "white",
        textDecoration: "none",
        marginLeft: "20px",
        fontSize: "0.75em",
        lineHeight: "1.75em"
    }
};


// the children will be the paths to the different pages
const Header = ({ children }) => {
    const { user } = useUserContext();

    return (
        <div style={styleSheet.root}>
            <div style={styleSheet.left}>
                <Link style={styleSheet.left} to="/">
                    <Logo color="#ffffff" size="50px" />
                    <div style={styleSheet.name}>TrekEngine</div>
                </Link>
            </div>
            <div style={styleSheet.right}>
                {children.map(l => cloneElement(l, { style: styleSheet.Link }))}
                {user ? <LogoutButton /> : <LoginButton />}
            </div>
        </div>
    )
}

export default Header;
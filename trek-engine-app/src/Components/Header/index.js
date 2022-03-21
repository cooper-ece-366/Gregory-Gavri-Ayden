import LoginButton from "./LoginButton"
import Logo from "../Utils/Logo"
import { Link } from "react-router-dom";

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
        boxSizing: "border-box"
    },
    left: {
        display: "flex",
        justifyContent: "center",
        alignSelf: "center",
        alignContent: "center"
    },
    name: {
        lineHeight: "50px",
        marginLeft: "10px",
        color: "white",
    },
    Link: {
        color: "white",
        textDecoration: "none",
        marginLeft: "20px",
    }
};

const pages = [
    {
        path: "/",
        name: "Home"
    },
    {
        path: "/tripgen",
        name: "TripGen"
    },
    {
        path: "/about",
        name: "About Us"
    }
];
// the children will be the paths to the different pages
const Header = ({ children }) => {
    return (
        <div style={styleSheet.root}>
            <div style={styleSheet.left}>
                <Logo color="#ffffff" size="50px" />
                <div style={styleSheet.name}>TrekEngine</div>
            </div>
            <div>
                {pages.map(page => <Link style={styleSheet.Link} to={page.path} >{page.name}</Link>)}
            </div>
            {/* <div style={styleSheet.leftRoot}>
                <div style={styleSheet.linkRoot}>
                    
                </div>

            </div>
            <div>TrekEngine </div>
            <div> <LoginButton /> </div> */}
        </div>
    )
}

export default Header;
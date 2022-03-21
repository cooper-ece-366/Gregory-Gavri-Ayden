import LoginButton from "./LoginButton"
import Logo from "../Utils/Logo"
import { Link } from "react-router-dom";
import LogoutButton from "./LogoutButton";
import { useUserContext } from "../../Contexts/UserContext";

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
        alignContent: "center"
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
        path: "/explore",
        name: "Explore"
    },
    {
        path: "/about",
        name: "About Us"
    }
];

// the children will be the paths to the different pages
const Header = ({children})=>{
    const {user} = useUserContext();

    return (
          <div style={styleSheet.root}>
              <div style={styleSheet.left}>
                  <Logo color="#ffffff" size="50px" />
                  <div style={styleSheet.name}>TrekEngine</div>
              </div>
              <div style={styleSheet.right}>
                  {pages.map(page => <Link style={styleSheet.Link} to={page.path} >{page.name}</Link>)}
                  <LoginButton />
              </div>
          </div>
      )
}

export default Header;
import LoginButton from "./LoginButton";
import LogoutButton from "./LogoutButton";
import { useUserContext } from "../../Contexts/UserContext";

const styleSheet = {
    root: {
        position: "fixed", 
        width: "100%",
        height: "6%", 
        backgroundColor: "#92a",
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        top: "0", 
        left: "0",
        fontSize: "2em"
    }, 
    leftRoot: {
        display: "flex", 
        paddingLeft: "12px"
    }, 
    left: {
        paddingRight: "3px",
        marginRight: "40px",
    }, 
    linkRoot: {
        display: "flex", 
    },
    link : {
        border: "1px solid black",
        paddingRight: "10px",
        marginRight: "30px",
    }

}; 

// the children will be the paths to the different pages
const Header = ({children})=>{
    const {user} = useUserContext();
    return (
        <div style = {styleSheet.root}>
            <div style= {styleSheet.leftRoot}>
                <div style = {styleSheet.left}>Logo Here </div>
                <div style= {styleSheet.linkRoot}>
                    {children.map(child=><div style = {styleSheet.link}>{child}</div>)}
                </div>
                
            </div>
            <div>TrekEngine </div> 
            <div> {user ? <> Welcome {user.firstName} <LogoutButton /> </>: <LoginButton />} </div>
        </div>
    )
}

export default Header;
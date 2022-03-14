import LoginButton from "./LoginButton"

const styleSheet = {
    root: {
        position: "fixed", 
        width: "100%",
        height: "50px", 
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
        // borderRight: "3px solid black",
        paddingRight: "3px",
        marginRight: "40px",
    }, 
    linkRoot: {
        display: "flex", 
        // flexGrow: "1",
    },
    link : {
        border: "1px solid black",
        paddingRight: "10px",
        marginRight: "30px",
    }

}; 

// the children will be the paths to the different pages
const Header = ({children})=>{
    return (
        <div style = {styleSheet.root}>
            <div style= {styleSheet.leftRoot}>
                <div style = {styleSheet.left}>
                    <img src="/assets/logo/blanklogo.svg" height="40" width="40"></img>
                </div>
                <div style= {styleSheet.linkRoot}>
                    {children.map(child=><div style = {styleSheet.link}>{child}</div>)}
                </div>
                
            </div>
            <div>TrekEngine </div> 
            <div> <LoginButton /> </div>
        </div>
    )
}

export default Header;
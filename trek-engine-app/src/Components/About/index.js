// Written By Gavri Kepets
import Map from "../Utils/Map"; 
import Logo from "../Utils/Logo";

const styleSheet = {
    fullPage: {
        width: "100%",
        height:"100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop:"70px",
        width:"25%"
    },
    about: {
        width: "80%",
        height: "80%",
        position: "absolute",
        top: "10%",
        left: "10%",
        backgroundColor: "rgba(100,100,100,0.75)",
        borderRadius: "10px",
        textAlign: "center",
        color: "white",
    }
};


const About = () => {
    return (
        <div style={styleSheet.fullPage}>
            <Map/>
            <div style={styleSheet.about}>
                <h1>Welcome to TrekEngine!</h1>
                <h3>TrekEngine provides a fast, smart, and simple solution for planning your next adventure.</h3>
                <p>TrekEngine was built as a school project for Software Engineering at Cooper Union.</p>
                <p>TrekEngine was created by Gregory Presser, Ayden Shankman, and Gavri Kepets</p>
                <Logo size ={250} color={"white"}/>
            </div>
        </div>
    );
}; 

export default About;
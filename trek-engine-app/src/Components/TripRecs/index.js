import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";

const styleSheet = {
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop: "70px",
        width: "25%"
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
            <Map />
            <FloatingMenu>
            </FloatingMenu>
        </div>
    );
};

export default About;
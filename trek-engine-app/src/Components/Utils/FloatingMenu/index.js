// Written by Gavri Kepets
const styleSheet = {
    floatingMenu: {
        position: "fixed",
        zIndex: "2",
        height: "80%",
        width: "15%",
        maxWidth: "450px",
        minWidth: "200px",
        backgroundColor: "#ffffff",
        borderRadius: "10px",
        bottom: "10%",
        left: "2.5%",
        minWidth: "250px",
        boxShadow: "0px 0px 50px #000000",
        display: "flex",
        background: "linear-gradient(180deg, #050D2B 0%, #010514 100%)",
        color: "white",
    }
}
const FloatingMenu = ({ children, style = {} }) => (
    <div style={{ ...styleSheet.floatingMenu, ...style }}>
        {children}
    </div>
)

export default FloatingMenu; 
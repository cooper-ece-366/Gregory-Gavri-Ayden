const styleSheet = {
    floatingMenu:{
        position: "fixed",
        zIndex: "2",
        height: "80%",
        width: "20%",
        backgroundColor: "#ffffff",
        borderRadius: "10px",
        bottom: "10%",
        left: "2.5%",
        minWidth: "200px",
        boxShadow: "0px 0px 50px #000000",
        display: "flex",
    }
}
const FloatingMenu = ({children, style = {}}) => (
    <div style={{...styleSheet.floatingMenu, ...style}}>
        {children}
    </div>
)

export default FloatingMenu; 
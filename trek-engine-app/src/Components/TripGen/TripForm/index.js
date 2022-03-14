const styleSheet = {
    container : {
        width: "200px",
    },
    form : {
        display: "flex",
        flexDirection: "column",
    }
    
}

const TripForm = (props) => {
    return (
        <div style = {styleSheet.container}>
            <h1>Build Your Trip Here!</h1>
            <form style={styleSheet.form}>
                From
                <input type="text" placeholder="Start Location"></input>
                To
                <input type="text" placeholder="End Location"></input>
                Make sure I visit...
                <textarea placeholder="Type mandatory locations here"></textarea>
                I prefer to visit...
                <textarea placeholder="National Parks, Big Cities, Famous restaraunts..."></textarea>
            </form>
            {/* Start Location | End Location | Trip Length | Advanced Options     */}
        </div>
    )
}

export default TripForm;
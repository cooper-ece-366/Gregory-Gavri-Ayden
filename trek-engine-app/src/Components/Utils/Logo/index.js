const Logo = (props) => {
    return (
        <svg height={props.size} width={props.size} id="epVghbsCdTz1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 1000" shapeRendering="geometricPrecision" textRendering="geometricPrecision">
            <ellipse rx="275.256176" ry="275.256176" transform="matrix(1.625367 0 0 1.625367 500 500)" fill="none" stroke={props.color} strokeWidth="35" />
            <path fill={props.color} d="M500,87.5L543.30127,500L500,912.5L456.69873,500L500,87.5Zm0,427.10233c8.06464,0,14.60233-6.53768,14.60233-14.60233s-6.53768-14.60233-14.60233-14.60233-14.60233,6.53768-14.60233,14.60233s6.53768,14.60233,14.60233,14.60233Z" transform="matrix(.965926 0.258819-.258819 0.965926 146.446609-112.372436)" strokeWidth="0" />
            <path fill={props.color} d="M718.28818,269.0271l50.73892-50.73892l12.68473,12.68473-50.73892,50.73892-12.68473-12.68473ZM281.71183,730.97291l-50.73892,50.73892-12.68473-12.68473l50.73892-50.73892l12.68473,12.68473Zm449.26108-12.68474l50.73892,50.73892-12.68473,12.68473-50.73892-50.73892l12.68473-12.68473ZM269.0271,281.71182L218.28818,230.9729l12.68473-12.68473l50.73892,50.73892-12.68473,12.68473Z" strokeWidth="0" />
        </svg>
    )
}

export default Logo;
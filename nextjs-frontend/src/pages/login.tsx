import { signIn } from "next-auth/react";
import { useEffect } from "react";

// TODO: callbackUrl in .env

const Home = () => {
    useEffect( () => {
        signIn( "spotify", { callbackUrl: 'http://127.0.0.1:3000' } )
    }, [] )

	return <div></div>
}

export default Home;
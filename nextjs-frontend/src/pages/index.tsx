import { GetServerSideProps } from "next";
import { getSession } from "next-auth/react";

import { MySession } from "~/session";

import spotify from "./api/spotify";
import { isAuthenticated } from "~/session";
import SignOut from "~/components/SignOut";

interface User {
	name: string;
	followers: number;
	image: string;
	subscription: string;
}

export const getServerSideProps: GetServerSideProps = async (ctx) => {
	const session = await getSession(ctx) as MySession
	const logged = await isAuthenticated(session)
	
	if ( !logged ) {
		return {
			redirect: {
				destination: "/login",
				permanent: false,
			},
		};
	}
	
	const me = await spotify(session).me();

    const { display_name, followers, images, product } = me;

    const user: User = {
        name: display_name,
        followers,
        image: images[0].url,
        subscription: product
    }

	return { props: { me: user } };
}

export default function( { me }: { me: User } )
{
	console.log('here', me);
	
	<div style={{position: 'absolute', left: '10px', top: '10px', color: 'white'}}>
		<SignOut />
		Home page
		{JSON.stringify(me)}
	</div>
}
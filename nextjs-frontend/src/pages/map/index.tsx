import { GetServerSideProps } from "next";
import { getSession } from "next-auth/react";
import { isAuthenticated, MySession } from "~/session";
import spotify from "../api/spotify";
import DynamicMap from '../../components/DynamicMap';


// export const getServerSideProps: GetServerSideProps = async (ctx) => {
// 	const session = await getSession(ctx) as MySession
// 	const logged = await isAuthenticated(session)
	
// 	if ( !logged ) {
// 		return {
// 			redirect: {
// 				destination: "/login",
// 				permanent: false,
// 			},
// 		};
// 	}

//     const res = 'server side props template'

// 	return { props: { res } };
// }

export default function foo() {
	return <DynamicMap />
}

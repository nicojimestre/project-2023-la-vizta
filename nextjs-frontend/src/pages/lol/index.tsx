import { GetServerSideProps } from "next";
import { getSession } from "next-auth/react";
import { isAuthenticated, MySession } from "~/session";
import spotify from "../api/spotify";


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
	
	// const data = await spotify(session).search('let you down', 'nf');
    // const track = data.tracks.items[0]
    // const res = {
    //     id: track.id,
    //     release_date: track.album.release_date,
    //     name: track.name,
    //     popularity: track.popularity,
    //     image: track.album.images[0].url
    // }

    const track = await spotify(session).tracks('6uFsE1JgZ20EXyU0JQZbUR')
    const album_id = track.album.id
    const album = await spotify(session).albums(album_id)
    const artists = await Promise.all( track.artists.map( (artist: any) => 
        spotify(session).artists(artist.id)
    ) )
    
    const genres = artists.map( (artist: any) => artist.genres ).flat()

    console.log(artists);
    console.log(genres);

    const res = {
        id: track.id,
        album_id: album_id,
        name: track.name,
        genres: album.genres        
    }

	return { props: { res } };
}

export default function( { res }: { res: any } )
{
    console.log(res);
    return <div>{JSON.stringify(res, undefined, 4)}</div>
}
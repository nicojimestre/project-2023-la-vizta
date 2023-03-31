import { MySession } from "../../session";

const SPOTIFY_URL = 'https://api.spotify.com/v1'

const spotifyFetch = async (path: string, session: MySession, fetchOptions: any = {} ) => {
    if ( session.user === undefined ) return { json: () => ({ code: '500', err: 'User not defined' }) }
    const init = { headers: {  Authorization: `Bearer ${session.user.accessToken}` } }
    const resp = await fetch( SPOTIFY_URL + path, { ...init, ...fetchOptions } )
    return resp
}

const spotifyGet = async (path: string, session: MySession) => {
    const resp = await spotifyFetch(path, session)
    return await resp.json()
}

const spotifyPost = async (path: string, session: MySession ) => {
    const fetchOptions = { method: "POST" }
    return await spotifyFetch(path, session, fetchOptions)
} 

const spotifyPut = async (path: string, session: MySession, body: any = {}, query: any = '' ) => {
    const fetchOptions = {
        method: "PUT",
        body: JSON.stringify(body),
    }
    return await spotifyFetch(path + query, session, fetchOptions)
}

const spotify = (session: MySession) => { 
    return {
        me: async () => await spotifyGet('/me', session),
        search: async (title: string, artist: string) => {
            const path = `/search?query=${title}+${artist}&type=track&limit=1`
            return await spotifyGet(path, session)
        },
        tracks: async (id: string) => {
            const path = `/tracks/${id}`
            return await spotifyGet(path, session)
        },
        albums: async (id: string) => {
            const path = `/albums/${id}`
            return await spotifyGet(path, session)
        },
        artists: async (id: string) => {
            const path = `/artists/${id}`
            return await spotifyGet(path, session)
        }
    }
}

export default spotify;

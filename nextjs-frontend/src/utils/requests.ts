import axios from 'axios'

export const get = (query: string, params: any, callback?: (data: any) => void) => {
    console.log('[GET] ', query, params);
    axios.get(query, { params } )
        .then( res => callback ? callback(res.data) : null )
}

export const asyncget = async (query: string, params: any) => {
    console.log('[async GET] ', query, params);
    const res = await axios.get(query, { params } )
    return res.data
}

import useSWR from 'swr'

// TODO: generic when deploying
const URI = 'http://localhost:9000'
const fetcher = (...args) => fetch(...args).then(res => res.json())

export default function useFetch( path: string ) 
{
    const { data, error, isLoading } = useSWR(`${URI}${path}`, fetcher)
    return { data, error, isLoading }
}
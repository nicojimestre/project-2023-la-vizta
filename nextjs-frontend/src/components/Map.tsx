import { useState, useEffect} from "react";
import { MapContainer, TileLayer, GeoJSON } from "react-leaflet";
import L from 'leaflet'

import RegionPopup from './RegionPopup'
import Navbar from './Navbar';
import useFetch from '~/hooks/useFetch';

import { Track, GenreName, Color, RegionGeometry, Region } from '~/types';

import 'leaflet/dist/leaflet.css';

const mapOptions = {
    center: {lat: 46.801111, lng: 8.226667},
    zoom: 3,
    minZoom: 2,
    maxZoom: 5,
    maxBounds: L.latLngBounds(
        L.latLng(-100, -180), 
        L.latLng(100, 180)
    ),
}

const palette: {[genre: GenreName]: Color} = {
    'Alternative': '#f55dd4ff', // --purple-pizzazz: #f55dd4ff;
    'Arabic':      '#384dabff', // --violet-blue: #384dabff;
    'Bollywood':   '#3fd7dfff', // --robin-egg-blue: #3fd7dfff;
    'Hip-Hop/Rap': '#2fdf81ff', // --emerald: #2fdf81ff;
    'Latino':      '#0000ffff', //  blue =    --dark-pastel-green: #42b83eff;
    'Pop':         '#f3e32cff', // --aureolin: #f3e32cff;
    'Pop/Rock':    '#f78a40ff', // --orange-wheel: #f78a40ff;
    'K-Pop':       '#ff4e3eff', // --tomato: #ff4e3eff;
    'J-Pop':       '#7a3a0fff', // --russet: #7a3a0fff;
}


// function groupByKey<V, K extends keyof V>(arr: V[], key: K) 
// {
//     return arr.reduce( (acc, cur) => {
//         const v = cur[key] as K
//         acc.set(v, [...(acc.get(v) as V[] || []), cur] )
//         return acc
//     }, new Map<K, V[]>() )
// }

// function mapValues<K, V, U>(map: Map<K, V>, f: (v: V) => U): Map<K, U>
// {
//     const res = new Map<K, U>()
//     for (let [k, v] of map) {
//         res.set(k, f(v)) 
//     }
//     return res
// }


type TracksPerRegion = {[region: Region]: Track[]}
type TopGenresPerRegion = {[region: Region]: GenreName[]}

const MapWrapper = () => {
    const { data: regions, isLoading: isRegionsLoading } = useFetch('/country/all')
    const { data: genres, isLoading: isGenresLoading } = useFetch('/genres')

    return (
        <MapContainer 
            {...mapOptions}
            style={{height: '100vh', border: undefined}}
            boundsOptions={{
                maxBoundsViscosity: 0.5,
            }}
            // TODO: fractional zooming without weird separation between tiles
            // zoomSnap={0.5}
            // zoomDelta={0.25}
            preferCanvas={true} 
        >
            <TileLayer
                className="grayscale-tilelayer"
                minZoom={2}
                maxZoom={5}
                zoomSnap={0.1}
                // TODO: thunderforest tilelayer
                // subdomains='abcd'
                // url='https://stamen-tiles-{s}.a.ssl.fastly.net/terrain-background/{z}/{x}/{y}{r}.png'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Navbar />
            { !isRegionsLoading && !isGenresLoading && regions.map( ([regionName, geometry], i) => {
                const tracks = genres.tracksPerRegion[regionName] || []
                const topGenres = genres.topGenresPerRegion[regionName] || []
                const topGenre = topGenres && topGenres[0]
                const color = palette[topGenre] || 'white'
                return <GeoJSON key={`country-${i}`} style={{fillColor: color, color, weight: 1}} data={JSON.parse(geometry)}>
                    <RegionPopup region={regionName} topGenres={topGenres} tracks={tracks} />
                </GeoJSON>
            }
            ) }
        </MapContainer>
    )
}

export default MapWrapper;
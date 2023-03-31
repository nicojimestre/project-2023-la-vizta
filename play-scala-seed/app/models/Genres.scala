package models

import models.Types.{Artist, Genre, GenreId, Rank, Region, Streams, Title}

case class Genres(
                  id: GenreId,
				  title: Title,
				  artist: Artist,
				  region: Region,
				  streams: Streams,
				  ranking: Rank,
				  genre: Genre );

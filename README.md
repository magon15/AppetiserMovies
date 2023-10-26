
# Appetiser Movies

A simple mock movie application made by Maervince A. Gonzales as part of a coding challenge created by the Appetiser team.


![Logo](https://i.ibb.co/d60Mzhx/appetiser-movies-logo.png)

## Features

- Search movies from iTunes API in the 'Movies' section
- Your favorites are reflected dynamically across all screens, mainly due to Kotlin's Flow feature.
- View your favorites and recently viewed movies offline in the 'Favorites' section.

## Tech Stack

- Kotlin
- Coroutines
- Livedata & ViewModel
- MVVM
- Repository Pattern 
- Room
- Retrofit and OkHttp
- Hilt Dependency Injection
- DataStore
- Epoxy RecyclerView
## Persistence

#### General Approach
The app mainly persists data with the help of Room database. Through it we can store the user's favorite movies as well as his recently viewed movies.

I decided to create a separate movie data class object, `MovieLocal.kt`, and this will be used to persist the movie object locally into the database. The only difference with the remote data class object, `Movie.kt`, are the `isFavorite` and `dateLastViewed` parameters. I decided to go with this approach because of the limitations of EpoxyRecyclerView, since it shows an error when we manually change the parameters of the remote movie object.

#### Favorites
The user can set his favorite movies on the movie search screen, movie details screen as well as the favorites screen and recently viewed screen. The user's favorites are reflected dynamically across all screens thanks to Kotlin's Flow feature, which triggers every time there are changes made in the locally stored movie objects.

#### Recently Viewed Movies
Every time the user opens the movie details screen, the app stores the remote movie object locally and sets the `dateLastViewed` parameter to the current date in milliseconds. I took use of Room's nifty feature of limiting our query to just the 10 latest results, since we don't want to show all the user's visited movies. Below is my query for the recently viewed movies:

```bash
  @Query("SELECT * FROM ${MovieLocal.DB_KEY} WHERE dateLastViewed IS NOT NULL ORDER BY dateLastViewed DESC LIMIT 10")
```

#### Last Search
I also added a feature where the user can store his last searched movie. I used Android's DataStore library which can store small sets of data without requiring the need for a database. 

Every time the user makes a query in the search bar, it gets stored on the DataStore. Once the app restores, his last search will be reflected on the screen.

#### Rooms for Improvement
In the future I could add a feature where I could delete the unnecessarily persisted movie objects, like the movies that are not set to favorite by the user as well as the movies that are viewed by the user long ago. But for now I'm happy where it's at.





## Architecture
Model-View-ViewModel (MVVM) architecture allows my code to be more readable, segregated and scalable. Readable because I don't have bulky lines of code in one activity and it's easier to debug and find what classes are causing issues. Segregated because MVVM allows my code to have separation of concerns, meaning a class is only doing what it's supposed to. The activity is there to interact with the user, the data source classes are there to interact with the user's data, and the ViewModel classes are there to mediate between the view and data classes. MVVM also allows this app to be scalable because it makes the code easy to unit test in case I want to implement it in the future.

The only downside I could think of is that for a very small application like this, MVVM is probably way too overkill given that I also didn't implement unit testing with this app. Probably MVP would be more appropriate, but I think this architecture works fine for this app.
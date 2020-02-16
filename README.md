# LBC-Technical-Test

## Architecture Overview:
The architecture is inspired from the Clean Architecture Manifesto based on the new Android Architecture Components, here is how the project implements it :
![architecture](https://github.com/bachiri/LBC-Technical-Test/blob/master/githubAssets/LBC%20Android%20Technical%20Chalenge.jpg)

1. **Layer : User Interface** This part is responsible for displaying the data provided by the ViewModel. It contains a simple RecyclerView with It’s Adapter.
2. **Presentation** Relies on the Android Architecture View Model Component that Handles The persistence of data when the configuration of the device changes ,means we will still have the elements we already fetched and the pages we already loaded
3. **Domain** Handles the pagination internally using the repository method getAlbums(from,to) 
4. **Domain** Fetch and Persist All Albums gotten from the API Endpoint 
5. **Data** Repository is the layer handling the data either from the network or locally 
6. **Data** locally we use Room as a database.
7. **Data** Remotely we fetch data using Retrofit http client.
8. (**User Interface**) , 9(**Presentation**), 10(**Domain**) - Handles displaying a single Album

**AlbumEntity** : is used in the Local Repository and within the whole app (Album) is used. 
**LiveData** is used in the **Presentation** And User Interface layer and  RxJava is used beneath **Presentation**.

## Functionalities:
- Display Albums (Demo)  

- Display one Album (Demo)

Demo: https://github.com/bachiri/LBC-Technical-Test/blob/master/githubAssets/Demo.gif

Libraries Used 
* RxJava 2
* Dagger 2 
* Retrofit 
* Picasso
* Gson
* Android Support Library
* Android Architecture Components 
	* LiveData
	* ViewModel
* Room 
* Mockito 

## Tests :
Layers 
 - [x] Repository
	 - [x] Remote 
	 - [x] Local (Room)
 - [x] Domain
 - [x] Presentation
- [ ] User Interface 


## What Can be Improved when the project is envisioned to grow : 
* Seperate each Layer in a module Repository / Domain / Presentation
* Optimize the First Start Up so each element Inserted should be displayed.
* Improve the User Interface.
* Create an Abstract common Use Case that can support code evolution, like centralize the use case startup in a single method execute.
* Set Up a more con wiring architecture for DI.

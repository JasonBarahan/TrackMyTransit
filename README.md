<div align='center'>
    <!-- project title is up for debate!! -->
    <h1><b>TrackMyTransit</b></h1>
    <div>A project by Aaron Aranda, Carmen Chau, Jacquelyn Lu, and Jason Barahan</div>
</div>

<!-- TODO: place graphics here once a working product is established -->

## Project Domain
Our project domain focuses on public transportation. We are developing a transit tracking application specific to the GO Transit network that allows commuters to find and receive data, so they can make well-informed decisions.

## The application
Our team plans to develop a GUI-based application that allows users to input the name of a GO Train station, and receive information such as:

- The different available “features” at this station
- A list that includes incoming trains and buses to that station

Furthermore, users will be allowed to access more information about each individual incoming train and bus to the station they are viewing, this includes:

- A map plotting the location of the incoming train or bus (using the latitude and longitude coordinates) 
- Getting the estimated time of arrival AND the scheduled time of arrive of the train/bus to that station. Then, returning the time delay (ie: The difference between these 2 numbers) 

This involves retrieving input data from users, organizing it into and retrieving data from API calls, displaying and visualizing data, and writing data (for frequently searched routes on a per-device basis).

## API
The API we are using is provided by <a href="http://api.openmetrolinx.com/OpenDataAPI/Help/Index/en">GO Transit</a>.

A sample API call can be seen below (note: The actual API key that was used to generate this output has been omitted in screenshot for security reasons)

GET REQUEST input: `http://api.openmetrolinx.com/OpenDataAPI/api/V1/Stop/NextService/UN?key=` (after the "=" sign, the API key is inserted)

<img width="421" alt="Screenshot 2023-10-22 at 6 53 04 PM" src="https://github.com/JasonBarahan/TrackMyTransit/assets/80921817/ae450211-dbd4-4f00-9149-0aa105319675">


## Footnotes
<ul>
    <li> Our primary focus is on train services. We will focus on bus services to the extent that they substitute train services - one example is Barrie Line service being operated by buses outside of rush hour. Bus services not associated with train lines will not be included (yet).</li>
    <li> This application is not endorsed by Metrolinx or any transit agency under the purview of Metrolinx.
</ul>

### Links
<!-- Some of the links here are empty and need to be filled. -->
<ul>
    <li><a href="hthttp://api.openmetrolinx.com/OpenDataAPI/Help/Index/en">API documentation</a> courtesy of Metrolinx</li>
    <li>Disclaimer #1: <br>"Route and arrival data used in this product or service is provided by permission of Metrolinx. Metrolinx assumes no responsibility for the accuracy or currency of the Data used in this product or service.”</li>
    <li>Disclaimer #2: <br>"This application is for study purposes only.”</li>
</ul>

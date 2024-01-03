<div align='center'>
    <!-- project title is up for debate!! -->
    <h1><b>TrackMyTransit</b></h1>
    <div>A project by Aaron Aranda, Carmen Chau, Jacquelyn Lu, and Jason Barahan
<br><a href="https://jasonbarahan.github.io/projects/trackmytransit/main.html">Project website</a></div>
</div>

<!-- TODO: place graphics here once a working product is established -->
<h2> About </h2>
<p> TrackMyTransit is a group project for <a href="https://artsci.calendar.utoronto.ca/course/csc207h1">CSC207</a>
    done by Aaron Aranda, Carmen Chau, Jacquelyn Lu, and Jason Barahan. (Their GitHub/LinkedIn profiles are
linked at the bottom of the page.)</p>

<p> It is a GUI-based transport tracking application specific to the GO Transit network which relies on obtaining
data using GET calls from <a href="http://api.openmetrolinx.com/OpenDataAPI/Help/Index/en"> GO Transit's own API</a>,
    processing relevant information - like station amenities and incoming train departures - and displaying it to
    the user. This program also relies on Bing's <i>REST Map Imagery API</i>.</p>

<p> Of course, we don't plan on monetizing this. This was purely for our own educational purposes.</p>

<h2>How does it work?</h2>
Users can input a GO Train station name and receive information about it, such as:
<ul>
    <li>The station's amenities</li>
    <li>The next departures from the train station</li>
    <li>Information on departure delays</li>
    <li>Visualizing those train positions on a map</li>
</ul>
<br>
A visual demonstration of the program can be found in the slides posted <a href="https://docs.google.com/presentation/d/11Zj77PYk7ggaJpTGcc7wBB_cS97FSSqaqtUPnrTcXIU/edit?usp=sharing">here.</a>
<h2>Design elements (and the project's purpose)</h2>
<p>CSC207 is a course on software design - which taught us various principles such as Clean Architecture, SOLID
design principles, design patterns, and object-oriented design. The course also focused on concepts such as
    RESTful APIs. This project was meant to demonstrate and further develop our understanding of these principles
    and concepts. A high-level overview is as follows:</p>
<ul>
    <li>Our program was created based on Clean Architecture and object-oriented design patterns. Classes were
    organized based on Clean Architecture layers and implemented such that data is passed through layers at
    specific points.</li>
    <ul>
        <li>To be more specific, we used the Model-View-Controller (MVC) pattern when deciding how to organize
        our classes.</li>
    </ul>
    <li>Packages within our program were also influenced by Clean Architecture. The outer
        layer was based on Clean Archiecture layer names. The inner layer within these packages were named after
        specific use cases, such as visualization of trains. This minimized ambiguity within our codebase and
        minimized time lost in the development timeline due to searching for specific files.</li>
    <li>Various design patterns, such as <i>Factory</i> and <i>Observer</i> were used. Their UML diagrams can be
    found in the <a href="resources.html">slides.</a></li>
    <li>SOLID design principles were used to ensure the program could be easily modified in the future without
    compromising the functionality of existing code, along other reasons. Some highlighted examples can be found
    in our <a href="resources.html">slides.</a></li>
</ul>

<h2>Acknowledgements</h2>
<ul>
    <li>GO Transit API courtesy of Metrolinx.</li>
    <li>BING Maps API courtesy of Bing.</li>
</ul>

<h2> Disclaimers </h2>
<ul>
    <li>We are not affiliated with GO Transit, Metrolinx, or any other transit agency.</li>
    <li>This program is for educational purposes only.</li>
</ul>

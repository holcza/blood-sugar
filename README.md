<h1>Blood sugar API</h1>

<h2>Tracking blood sugar measurements for pregnant clients</h2>

<h3>Overview</h3>

<p>The application is designed for tracking the blood sugar measures of pregnant clients
 diagnosed for pregnancy diabetes.</p>

<p>It handles data of clients and blood sugar measurements connected to these clients.</p>

<p>This application could be utilized by medial centers and doctors in services giving to their pregnant patients.</p>

<p>The application could be developed further by</p>
<ul>
<li>including data about insulin consumed by clients(type, time and amount)</li>
<li>including other regular diagnostic information of pregnant clients like blood pressure and weight</li>
<li>extending the application for clients who are not pregnant</li>
<li>including security function (authorization and authentication)</li>
<li>including user interface for clients to update database with their own measurements</li>
<li>including blood sugar measurer hardware updating database automatically with data measured by client</li>
</ul>

<h3>Technical details</h3>

<p>The application includes the following technologies:</p>

<ul>
<li>Spring Boot</li>
<li>JPA repository</li>
<li>MariaDB database</li>
<li>Flyway migration</li>
<li>Integration tests using TestRestTemplate</li>
<li>Docker file supporting docker image build</li>
<li>Swagger UI for for API docs and for testing HTTP methods on URLs</li>
<li>Lombok</li>
</ul>

<p>The architect of the application:</p>

<ul>
<li>Three layers : controller, service and persistent layers</li>
<li>Two entity: Client and Measurement with 1:N connection</li>
<li>Two tables in database: clients and measurements. Measurements table includes the id of the client</li>
<li>Two Controller classes: one controller for each entity</li>
<li>Two Service classes: one service for each entity</li>
<li>Two Repository classes: one repository for each entity</li>
<li>DTO classes for communication about entities between layers and between users and the application</li>
<li>Validation of inputs and one self made validation</li>
</ul>

<p>Summary of URLs and HTTP methods used in the application:</p>

| URL        | HTTP method           | Description  |
| ------------- |:-------------:| -----:|
| /api/clients      | GET | List all the clients or filter on clients by name and/or TAJ number and/or pregnancy termination date|
| /api/clients      | POST      |   Create a client in database |
| /api/clients | DELETE      |    Delete all the clients from database |
| /api/clients/{id} | GET      |    Find a client by ID|
| /api/clients/{id} | PUT      |    Modify a client data by ID |
| /api/clients/{id} | DELETE      |    Delete a client by ID |
| /api/clients/{id}/measurements | GET     |    List all the measurements of a client or filter on measurements of a client by type and/or time of measurement |
| /api/clients/{id}/measurements     |    POST | Create a measurement for a client|
| /api/clients/{id}/measurements      |    DELETE | Delete all the measurements for a client |
| /api/clients/{id}/measurements/{id}     |    GET | Find a measurement by ID |
| /api/clients/{id}/measurements/{id}     |    PUT | Modify a measurement by ID |
| /api/clients/{id}/measurements/{id}      |    DELETE | Delete a measurement by ID |


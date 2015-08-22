CSAR_Repository
===============

Is part of the TOSCA Ecosystem. It bundles the different components (Winery, OpenTOSCA-Container, Vinothek) in a mutual interface. 

![csarrepo_screen](https://cloud.githubusercontent.com/assets/5188694/9422897/9a7f6f02-48a9-11e5-98a1-2d09d4705655.png)

It supplies following import/export features and integrations:
* Winery: Import / Export
* OpenTOSCA Container:
  * Push CSAR to Container
  * Access to ContainerAPI
  * Remove CSAR from Container
  * List uploaded CSARs in Container
  * List live data of OpenTOSCA Container
  * Access WSO2 to invoke plans
* Vinothek: Links to trigger Selfservice-Plans

## Installation / Build:

1. Clone the repository
2. Create a database in your MySQL Server
2. Change MySQL credentials and database name in the `CSAR_Repository/src/main/resources/hibernate.cfg.xml` 
3. Import the database dump from `/resources/dump.sql` 
3. Remove the .example from `CSAR_Repository/src/main/webapp/WEB-INF/repository.properties.example` and change the `csarFilePath` to the location where all csarfiles will get stored
3. Generate the WAR: Build the project with maven
  4. Run from the project root folder: `mvn generate-sources;mvn war:war`
  5. Deploy the WAR on Tomcat7
4. Access the application on http://tomcat-root/csarrepo and login with user: admin / password: admin

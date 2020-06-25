RUN-ONCO: A Highly Extensible Software Platform for Cancer Precision Medicine
===============================================

RUN-ONCO is a software platform for cancer precision medicine with integrated clinical records, biospecimens, and -omics data. The platform has been designed to be highly extensible plugins for both visualizations and analysis methods to help developers easily integrate a variety of third-party plugins. RUN-ONCO mainly focuses on supporting cancer center in hospitals and laboratories that allows users to customize and build their own system.

Requirements
------------
* Install Docker and Docker Compose.

Installation
------------
* Clone this repository to create a local copy on your computer.
* Open a terminal and `cd` to docker-and-compose folder in which `docker-compose.yml` is saved and run:

	```bash
	source env.sh && docker-compose up --build -d
	```
	
    _Optional:  Stop the Docker container_

	```bash
	docker-compose down -v
	```

Usage
------------
After the successful installation you can access the web application via http://localhost:6002. The default login information for a RUN-ONCO is:

* Username: `icbbe` 
* Password: `demo2019`

Live website
------------
- The RUN-ONCO is located at http://cucpbioinfo.cp.eng.chula.ac.th:6002.

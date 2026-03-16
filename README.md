# CGI Restaurant system
Pet project

[Project visualization/workflow](docs/WORKFLOW.md)\
[Project stack](docs/WORKFLOW.md#Stack)\
[Website Preview](docs/images/Preview.png)

## Installation
Clone the repository to your local machine\
``git clone https://github.com/No1Evil/CGI-Restaurant.git .``

## Running
**Make sure you have docker installed**

Build and start the containers *(inside the cloned repo)*\
``sudo docker compose up -d --build``

# Accessing the system
Once all containers are running, access the application at:
> Frontend: ``http://localhost`` or ``http://<your_server_ip>``

> API ``http://localhost/api/`` or ``http://<your_server_ip>``

# Giving admin rights to existing users

``docker exec -it postgres_db psql -U restaurant_admin -d my_restaurant_db``

``UPDATE users SET role = 'ADMIN' WHERE email = '<your-user-email>@gmail.com';``

``exit``

#### Fjodor Tsumakov
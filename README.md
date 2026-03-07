# CGI Restaurant system

## Project Workflow

Define the stack and visualize how the system should look like:

[Google drive (drawio model)](https://drive.google.com/file/d/1v_0PPJUE9ypQQdyPqu-0_dICQv7SPUP6/view?usp=sharing)

### Stack:

> Required: \
> Java LTS 25 \
> Spring boot:*

> Selected: \
> Spring web \
> React \
> JDBC

Updated the entity relation model
as we need to write an admin-panel it is good to have each entity an
update and creation time.
Also, it is good practice not to delete the entity from 
database completely, but to store in the database.
Made it has field isDeleted.
Also moved schema to snake case to prevent mismatch.

Storing huge scripts in the java code is garbage, so I did some research
on how to load resources in the Spring.

Yet there is string queries in the classes, but it would take time
to create a good query builder for them.
Currently, I want to dive into the SQL scripting and don't want to mess with
magic of libraries that do create queries by themselves.

Also to prevent users from booking the same timestamp we create
a transactional methods and to make a validation - we use a service.

Just for a CV, I want to show how can I make a scalable network
so for that reason I created another table - restaurants.
Currently, I think of that Rest-app will validate JWT tokens and will create
requests to GRPC-services (this is where scalability comes in), and the grpc-service
will be responsible for database queries

As I have implemented is_deleted we want to get
only active data, but also we want to get all active and inactive.
For now, I’ll stick only to getting all active data.

Security: Came in mind that password hash shouldn`t be
checked on sql side instead we check it in UserService

#### Fjodor Tsumakov
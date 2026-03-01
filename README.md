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
As we need to write an admin-panel it is good to have each entity a
update and creation time.
Also, it is good practice not to delete the entity from 
database completely, but to store in the database.
Made it has field isDeleted.


#### Fjodor Tsumakov
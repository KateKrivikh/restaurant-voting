# Restaurant voting system
Voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
  * If it is before 11:00 we assume that he changed his mind.
  * If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

## Admin options:
#### Restaurants
###### 1. Get all:
`curl -s http://localhost:8080/voting/rest/admin/restaurants --user admin@gmail.com:admin`
###### 2. Get by id:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002 --user admin@gmail.com:admin`
###### 3. Add new:
`curl -s http://localhost:8080/voting/rest/admin/restaurants -X POST -H "Content-Type: application/json" -d '{"name":"Anderson"}' --user admin@gmail.com:admin`
###### 4. Update existing:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002 -X PUT -H "Content-Type: application/json" -d '{"id":100002,"name":"Tkemali (east)"}' --user admin@gmail.com:admin`
###### 5. Remove existing:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100003 -X DELETE --user admin@gmail.com:admin` 

#### Dishes
###### 1. Get all:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002/dishes?date=2020-08-20 --user admin@gmail.com:admin`
###### 2. Get by id:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002/dishes/100004 --user admin@gmail.com:admin`
###### 3. Add new:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002/dishes -X POST -H "Content-Type: application/json" -d '{"date":"2020-08-20","name":"Potato soup","price":100}' --user admin@gmail.com:admin`
###### 4. Update existing:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002/dishes/100004 -X PUT -H "Content-Type: application/json" -d '{"id":100004,"date":"2020-08-20","name":"Chicken, lamb or veal kebab","price":630}' --user admin@gmail.com:admin`
###### 5. Remove existing:
`curl -s http://localhost:8080/voting/rest/admin/restaurants/100002/dishes/100005 -X DELETE --user admin@gmail.com:admin` 

#### Users
###### 1. Get all:
`curl -s http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`
###### 2. Get by id:
`curl -s http://localhost:8080/voting/rest/admin/users/100000 --user admin@gmail.com:admin`
###### 3. Add new:
`curl -s http://localhost:8080/voting/rest/admin/users -X POST -H "Content-Type: application/json" -d '{"name":"new","email":"new@gmail.com","password":"myPass_123","roles":["USER"]}' --user admin@gmail.com:admin`
###### 4. Update existing:
`curl -s http://localhost:8080/voting/rest/admin/users/100000 -X PUT -H "Content-Type: application/json" -d '{"id":100000,"name":"User","email":"user@gmail.com","password":"user","roles":["USER","ADMIN"]}' --user admin@gmail.com:admin`
###### 5. Remove existing:
`curl -s http://localhost:8080/voting/rest/admin/users/100000 -X DELETE --user admin@gmail.com:admin`
###### 6. Get by email:
`curl -s http://localhost:8080/voting/rest/admin/users/by?email=user@gmail.com --user admin@gmail.com:admin` 

---

## User options:
###### 1. Get menu:
`curl -s http://localhost:8080/voting/rest/menu?date=2020-08-20 --user user@gmail.com:user`
###### 2. Vote for a restaurant:
`curl -s http://localhost:8080/voting/rest/votes?restaurantId=100002 -X POST --user user@gmail.com:user`

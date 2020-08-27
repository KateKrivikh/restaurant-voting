# Restaurant voting system
Voting system for deciding where to have lunch.

## Admin options:
#### Restaurants
###### 1. Get all:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants`
###### 2. Get by id:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002`
###### 3. Add new:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants -X POST -H "Content-Type: application/json" -d '{"name":"Anderson"}'`
###### 4. Update existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002 -X PUT -H "Content-Type: application/json" -d '{"id":100002,"name":"Tkemali (east)"}'`
###### 5. Remove existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100003 -X DELETE` 

#### Dishes
###### 1. Get all:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002/dishes?date=2020-08-20`
###### 2. Get by id:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002/dishes/100004`
###### 3. Add new:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002/dishes -X POST -H "Content-Type: application/json" -d '{"date":"2020-08-20","name":"Potato soup","price":100}'`
###### 4. Update existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002/dishes/100004 -X PUT -H "Content-Type: application/json" -d '{"id":100004,"date":"2020-08-20","name":"Chicken, lamb or veal kebab","price":630}'`
###### 5. Remove existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/restaurants/100002/dishes/100005 -X DELETE` 

#### Users
###### 1. Get all:
`curl http://localhost:8080/restaurant-voting/rest/admin/users`
###### 2. Get by id:
`curl http://localhost:8080/restaurant-voting/rest/admin/users/100000`
###### 3. Add new:
`curl http://localhost:8080/restaurant-voting/rest/admin/users -X POST -H "Content-Type: application/json" -d '{"name":"new","email":"new@gmail.com","password":"myPass_123","roles":["USER"]}'`
###### 4. Update existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/users/100000 -X PUT -H "Content-Type: application/json" -d '{"id":100000,"name":"User","email":"user@gmail.com","password":"user","roles":["USER","ADMIN"]}'`
###### 5. Remove existing:
`curl http://localhost:8080/restaurant-voting/rest/admin/users/100000 -X DELETE`
###### 6. Get by email:
`curl http://localhost:8080/restaurant-voting/rest/admin/users/by?email=user@gmail.com` 

---

## User options:
###### 1. Get menu:
`curl http://localhost:8080/restaurant-voting/rest/menu?date=2020-08-20`
###### 2. Vote for a restaurant:
`curl http://localhost:8080/restaurant-voting/rest/votes?restaurantId=100002 -X POST`

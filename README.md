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

---

## User options:
###### 1. Get menu:
`curl http://localhost:8080/restaurant-voting/rest/menu?date=2020-08-20`
###### 2. Vote for a restaurant:
`curl http://localhost:8080/restaurant-voting/rest/votes?restaurantId=100002 -X POST`

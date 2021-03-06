The Challenge:

The main role of this API is to calculate real time statistic from the last 60 seconds. 
It consists of two APIs, one is called every time a transaction is made.
The other one returns the statistic based of the transactions of the last 60.


POST /transactions

Each Time a new transaction happened, this endpoint will be called.

Where:

amount is double specifying transaction amount
timestamp is epoch in millis in UTC time zone specifying transaction time
Request sample

POST /api/transactions HTTP/1.1
Content-Type: application/json

{
    "amount": 12.3,
    "timestamp": 1478192204000
}

Success response sample

HTTP/1.1 201 Created
Error response sample - timestamp is older than 60 seconds

HTTP/1.1 204 No Content


GET /statistics

This is the main endpoint of this task, this endpoint have to execute in constant time and memory (O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.

Request sample

GET /api/statistics HTTP/1.1
Accept: application/json
Response sample

HTTP/1.1 200 OK
Content-Type: application/json

{
    "sum": 1000,
    "avg": 100,
    "max": 200,
    "min": 50,
    "count": 10
}
Where:

sum is a double specifying the total sum of transaction value in the last 60 seconds
avg is a double specifying the average amount of transaction value in the last 60 seconds
max is a double specifying single highest transaction value in the last 60 seconds
min is a double specifying single lowest transaction value in the last 60 seconds
count is a long specifying the total number of transactions happened in the last 60 seconds.


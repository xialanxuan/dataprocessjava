# dataprocessjava

To better process the data, we first wrote a simple java code to find how many users and devices are in this data set.

Within the data set we have
There are 7 users in data parts. Each user has one or two devices.

We use java to preprocess all the data parts for each user. 

For each user, 
We gather time stamps for each device and store categories in the time stamp. 
The schema for each document is the following:

```
{
    timeStamp: integer
    categories: {
        RawHeartValue: string
        BodyTemp: string
        ...
    }
}
```

Then we use MongoDB to store data.



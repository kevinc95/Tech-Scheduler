# Tech Scheduler

This final group project was assigned to me by my Mobile Software Development (Android)  professor, Mr. Shuo Niu, at Virginia Tech. This was a group project I conceptualized and made with Matthew Szostak (szuzzah@vt.edu) and Mohamed Ibrahim (moibra16@vt.edu).

This is the Tech Scheduler, which allows the user to input their Virginia Tech schedule for the current day and return a Google Map with markers for all the locations the user needs to go to. Also on this map is the shortest walking path between each location in order of the user's schedule. Each walking path is color coded to indicate the type of weather the user will encounter while walking that path at the specified time in the day. 

This mobile applicaton was designed with Virginia Tech freshman and new transfer students being the target audience. We expected they may need to know how to get around campus in the beginning of the school year, and can use this app to guide them, while warning them of any weather conditions.

# Screen Map and Execution

When the application is opened up, we start off on the Main Screen (MainScreen.png). There are three two buttons to press: SCHEDULE MY DAY and WEATHER. The WEATHER button leads to the Current Weather Screen (CurrentWeatherScreen.png), which displays the current weather conditions of the Blacksburg area, including the temperature, the temperature it feels like, the humidity, and the forecast.

On the Main Screen, the SCHEDULE MY DAY button leads to the Schedule My Day Screen (ScheduleMyDayScreen.png). On this screen is where the user inputs their schedule. They add each building destination they need to go to for their classes one by one, and with each location, they pick the building from the location spinner, and the time that class starts and ends. Afterwards they click the ADD button, and the location with its start and end time is added to the list below. If the user made a mistake on one of their classes, they can press the UNDO button to remove the last location made in the list. If they wish to start their whole schedule over, they can press the CLEAR button to remove all the locations. The list below organizes the locations in order of start time.

Once the user is done creating their schedule, they can click the SHOW MAP button and head to the Map Screen (MapScreen.png). Here, a Google Map appears with all the locations marked and the paths defined and color coded. There's also a color key so the user can understand what each path color indicates. From the Schedule My Day Screen and the Map Screen for example, it looks like it's going to be cloudy walking from Cassell Coliseum to Pritchard Hall at 7 pm, and cloudy walking from Pritchard Hall to Newman Library at 8 pm.

Needless to say, this application will require that the user has access to the internet.

# Download

If you wish to download this project to your Android phone, you will need to have Android Studio installed on your computer. Then you will need to import this project into Android Studio. You will need to change the directory names to your name when it's imported because the project was under my directory structure. Also you may need to 'File -> Invalidate Cache / Restart' and 'Build -> Clean Project' in order to rebuild your project. 

Make sure your phone is placed in developer mode and connect it to your computer via USB. Android should register the phone. Then you can press the Run 'app' (Play) button, choose your phone to download the project, and it will install.
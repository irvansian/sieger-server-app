# SIEGER SERVER APP
Collaborators :<br>
* Irvan Sian Syah Putra
* Chen Zhang

**LOCALLY**
If you want run this app locally, you must have a Firebase project. Then you have to get the service account key, and put it inside this project.
This project runs on localost:8080

**REMOTE**
If you want to use the endpoint from our API, you can hit our url at ``https://sieger-teamthree.herokuapp.com/``, but before you use the endpoints, you
have to regiter from our mobile app. And you always have to put "Authorization" in your header and put "Bearer <JWT-token-from-firebase>".
In case you are not registered yet, and want to use our API, you can login with these two dummy user accounts.

```
Email : testing@gmail.com
Password : password
```
and
```
Email : dummy@gmail.com
Password : password
```
To get the token
Send an HTTP Post request to ``https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyDIIa-WlH1k9UMsheSc-kU7BSkpEbMRVXM``
with the following body, and get the idToken, it expires after one hour.
```javascript
{
  "email" : "testing@gmail.com",
  "password" : "password",
  "returnSecureToken" : true
}
```

## Explore the API Endpoints:<br>

Users
Method | URL | Description | Request Body
-------|-----|-------------|-------------
GET|/users/{username}|Get a user by username|
GET|/users?key={id}|Get a user by its id|
GET|/users/{username}/tournaments|Get a user's tournaments|
GET|/users/{username}/teams|Get a user's teams|
GET|/users/{username}/invitations|Get a user's invitations|
POST|/users|Create a new user|[JSON](#createnewuser)
PUT|/users/{username}|Update user detail|[JSON](#updateuserdetail)

Tournaments
Method | URL | Description | Request Body
-------|-----|-------------|-------------
GET|/tournaments/{tournamentName}|Get a tournament by its name|
GET|/tournaments?key={id}|Get a tournament by its id|
GET|/tournaments/{tournamentName}/participants|Get list of participants of a tournament|
GET|/tournaments/{tournamentName}/games|Get list of games of a tournament|
GET|/tournaments/{tournamentName}/games/{id}|Get a game of a tournament by its id|
POST|/tournaments|Create a new tournament|[JSON](#createnewtournament)
POST|/tournaments/{tournamentName}|Join or quit a tournament|[JSON join](#handleparticipationjoin), [JSON quit](#handleparticipationquit)
POST|/tournaments/{tournamentName}/games|Ask server to create games automatically|
~~PUT~~|~~/tournaments/{tournamentName}~~|Update tournament detail (not implemented, overkill, client too slow)|
PUT|/tournaments/{tournamentName}|Update result of a game|[JSON](#updateresultgame)
DELETE|/tournaments/{tournamentName}|Delete a tournament|
DELETE|/tournaments/{tournamentName}/games/{id}|Delete a game|

Teams
Method | URL | Description | Request Body
-------|-----|-------------|-------------
GET|/teams/{teamName}|Get team by name|
GET|/teams?key={id}|Get team by id|
GET|/teams/{teamName}/members|Get team members|
GET|/teams/{teamName}/tournaments|Get team tournaments|
GET|/teams/{teamName}/invitations|Get team invitations|
POST|/teams|Create new team|[JSON](#createnewteam)
POST|/teams/{teamName}|Join or quit the team|[JSON join](#jointeam), [JSON quit](#quitteam)
DELETE|/teams/{teamName}|Delete a team|

Invitations
Method | URL | Description | Request Body
-------|-----|-------------|-------------
POST|/invitations|Create a new invitation|[JSON](#createinvitation)
POST|/invitations/{id}|Accept or decline invitation|[JSON accept](#acceptinvitation), [JSON decline](#declineinvitation)

<a name="createnewuser"></a>Create a new user
```javascript
{
  "username" : "myusername",
  "surname" : "mysurname",
  "forename" : "myforename",
  "userId" : "myuserid"
}
```

<a name="updateuserdetail"></a>Update user detail
```javascript
{
  "username" : "newusername",
  "surname" : "newsurname",
  "forename" : "newforename"
}
```

<a name="createnewtournament"></a>Create new tournament
```javascript
{
  
}
```

<a name="handleparticipationjoin"></a>Join a tournament
```javascript
{
  "participation" : true
}
```

<a name="handleparticipationquit"></a>Quit a tournament
```javascript
{
  "participation" : true
}
```

<a name="updateresultgame"></a>Update result of a game
```javascript
{

}
```

<a name="createnewteam"></a>Create new team
```javascript
{
  "adminId" : "oijmd2in813",
  "name" : "myteamname",
  "password" : "myteampassword"
}
```

<a name="jointeam"></a>Join a team
```javascript
{
  "activity" : "join"
}
```

<a name="quitteam"></a>Quit a team
```javascript
{
  "activity" : "join"
}
```

<a name="acceptinvitation"></a>Accept invitation
```javascript
{
  "accept" : true
}
```

<a name="declineinvitation"></a>Decline invitation
```javascript
{
  "accept" : false
}
```






# Bumble

Depuis mongoDB, après avoir enregistré les profils 'getEncounters()', faire une requête pour ne retourner que ces profils 'has_user_voted':'true'. 
-> la requête est la suivante:

db.encounter.find({"user.their_vote":2},{"user.name":1,"user.age":1,"user.distance_short":1,"user.distance_long":1,"user.their_vote":1,"user.albums.photos.large_url":1,"user.user_id":1})

+

visuel quadrillage geographique

+

Tinder

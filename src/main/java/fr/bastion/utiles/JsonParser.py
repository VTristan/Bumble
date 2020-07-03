# Loading packages
import json
import codecs


# Opening of the outputfile
with codecs.open("D:/Sauvegardes_Programmes/GitHub_Sauvegardes/Bumble/src/main/resources/other/jeu2Test_4", encoding="utf8") as file:
    enconters_str = file.read().replace("\u00a0", "").replace("\u00c3", "A")  # I replace all NON-BREAKING SPACES (\xa0) by an empty string. The encoding problem with Non-Breaking Spaces(NBSP) it's when you print them, it return you this: '\xa0', not this: ' '. To write a NBSP, type on your keyboard this AltCode 'Alt+0+1+6+0'.
    file.close()


# Initialization of variables
log_date = enconters_str[0:21]
data_str = enconters_str[21:]
data_dict = json.loads(data_str)
dict_parsed = {}
end = len(data_dict["body"][0]["client_encounters"]["results"])


# Parsing's moment
for i in range(0, end):  # For each girl

    end2 = len(data_dict["body"][0]["client_encounters"]["results"][i]["user"]["albums"][0]["photos"])
    dict_parsed[i] = {
        "user_id"       : data_dict["body"][0]["client_encounters"]["results"][i]["user"]["user_id"],
        "name"          : data_dict["body"][0]["client_encounters"]["results"][i]["user"]["name"],
        "age"           : data_dict["body"][0]["client_encounters"]["results"][i]["user"]["age"],
        "distance_long" : data_dict["body"][0]["client_encounters"]["results"][i]["user"]["distance_long"],
        "distance_short": data_dict["body"][0]["client_encounters"]["results"][i]["user"]["distance_short"],
        "has_user_voted": data_dict["body"][0]["client_encounters"]["results"][i]["has_user_voted"],
        "their_vote"    : data_dict["body"][0]["client_encounters"]["results"][i]["user"]["their_vote"],
        "photos"        : [],
        "log_date"      : log_date
    }

    for j in range(0, end2):  # For each photos
        dict_parsed[i]["photos"].append(
            data_dict["body"][0]["client_encounters"]["results"][i]["user"]["albums"][0]["photos"][j]["large_url"])


# Exportation of data
with codecs.open("D:/Sauvegardes_Programmes/GitHub_Sauvegardes/Bumble/src/main/resources/other/json_of_girls.json", "w", encoding="utf8") as json_file:
    json.dump(dict_parsed, json_file)